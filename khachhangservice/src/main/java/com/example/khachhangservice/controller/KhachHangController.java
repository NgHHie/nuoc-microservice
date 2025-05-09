package com.example.khachhangservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.khachhangservice.client.ThongKeKhachHangClient;
import com.example.khachhangservice.model.CanHo;
import com.example.khachhangservice.model.KhachHang;
import com.example.khachhangservice.service.KhachHangService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@Transactional
@RequestMapping("/khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;
    
    @Autowired
    private ThongKeKhachHangClient thongKeKhachHangClient;

    // API hiện tại cho tương thích ngược
    @GetMapping
    public List<KhachHang> getAllKhachHang() {
        return khachHangService.getAllKhachHang();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHang> getKhachHangById(@PathVariable int id) {
        Optional<KhachHang> khachHang = khachHangService.getKhachHangById(id);
        return khachHang.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<KhachHang> getKhachHangByName(@RequestParam String name) {
        return khachHangService.getKhachHangByName(name);
    }
    
    // API mới hỗ trợ phân trang
    @GetMapping("/paged")
    public ResponseEntity<Map<String, Object>> getKhachHangPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String search) {
        
        try {
            page = page - 1;
            // Xử lý tham số sắp xếp
            Sort sortObj = Sort.by(Sort.Direction.ASC, "id"); // Mặc định sắp xếp theo ID tăng dần
            if (sort != null && !sort.isEmpty()) {
                try {
                    // Giả sử sort có định dạng JSON {"field": "fieldName", "sort": "asc/desc"}
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, String> sortMap = mapper.readValue(sort, Map.class);
                    
                    String sortField = sortMap.get("field");
                    String sortDirection = sortMap.get("sort");
                    
                    if (sortField != null && sortDirection != null) {
                        // Chỉ hỗ trợ các trường hợp lệ
                        if (isValidSortField(sortField)) {
                            Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) 
                                ? Sort.Direction.DESC : Sort.Direction.ASC;
                            sortObj = Sort.by(direction, sortField);
                        }
                    }
                } catch (JsonProcessingException e) {
                    // Bỏ qua lỗi và sử dụng sắp xếp mặc định
                    System.err.println("Error parsing sort parameter: " + e.getMessage());
                }
            }
            
            // Tạo Pageable
            Pageable pageable = PageRequest.of(page, size, sortObj);
            
            // Xử lý tham số tìm kiếm
            String searchTerm = null;
            String searchField = "hoten"; // Mặc định tìm theo tên
            
            if (search != null && !search.isEmpty()) {
                try {
                    // Giả sử search có định dạng JSON {"input": "searchTerm", "column": "fieldName"}
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, String> searchMap = mapper.readValue(search, Map.class);
                    
                    searchTerm = searchMap.get("input");
                    String column = searchMap.get("column");
                    
                    if (column != null && isValidSearchField(column)) {
                        searchField = column;
                    }
                } catch (JsonProcessingException e) {
                    // Bỏ qua lỗi và sử dụng tìm kiếm mặc định
                    System.err.println("Error parsing search parameter: " + e.getMessage());
                }
            }
            
            // Thực hiện tìm kiếm
            Page<KhachHang> khachHangPage = khachHangService.searchKhachHang(searchTerm, searchField, pageable);
            
            // Chuẩn bị kết quả
            Map<String, Object> response = new HashMap<>();
            response.put("content", khachHangPage.getContent());
            response.put("currentPage", khachHangPage.getNumber());
            response.put("totalItems", khachHangPage.getTotalElements());
            response.put("totalPages", khachHangPage.getTotalPages());
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<KhachHang> createKhachHang(@Valid @RequestBody KhachHang khachHang) {
        try {
            KhachHang kh = khachHangService.saveKhachHang(khachHang);
            thongKeKhachHangClient.updateKhachHang("create", kh);
            return new ResponseEntity<>(kh, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhachHang> updateKhachHang(@PathVariable int id, @Valid @RequestBody KhachHang khachHang) {
        Optional<KhachHang> existingKH = khachHangService.getKhachHangById(id);
        
        if (existingKH.isPresent()) {
            khachHang.setId(id);
            KhachHang kh = khachHangService.saveKhachHang(khachHang);
            thongKeKhachHangClient.updateKhachHang("update", kh);
            return new ResponseEntity<>(kh, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKhachHang(@PathVariable int id) {
        try {
            Optional<KhachHang> existingKH = khachHangService.getKhachHangById(id);
            if (!existingKH.isPresent()) {
                return new ResponseEntity<>("Không tìm thấy khách hàng với ID: " + id, HttpStatus.NOT_FOUND);
            }
            
            khachHangService.deleteKhachHang(id);
            KhachHang kh = new KhachHang();
            kh.setId(id);
            thongKeKhachHangClient.updateKhachHang("delete", kh);
            return new ResponseEntity<>("Khách hàng đã được xóa thành công!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi xóa khách hàng: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/searchbycanho/{canHoId}")
    public ResponseEntity<KhachHang> getKhachHangByCanHo(@PathVariable int canHoId) {
        try {
            CanHo canHo = new CanHo();
            canHo.setId(canHoId);
            KhachHang khachHang = khachHangService.getKhachHangByCanHo(canHo);
            
            if (khachHang != null) {
                return new ResponseEntity<>(khachHang, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Helper methods
    private boolean isValidSortField(String field) {
        return field.equals("id") || field.equals("hoten") || 
               field.equals("email") || field.equals("sodienthoai");
    }
    
    private boolean isValidSearchField(String field) {
        return field.equals("all") || field.equals("hoten") || 
               field.equals("email") || field.equals("sodienthoai");
    }
}