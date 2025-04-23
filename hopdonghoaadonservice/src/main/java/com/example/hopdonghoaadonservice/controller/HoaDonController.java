package com.example.hopdonghoaadonservice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hopdonghoaadonservice.model.HoaDon;
import com.example.hopdonghoaadonservice.service.HoaDonService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hoadon")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;
    
    @GetMapping
    public ResponseEntity<List<HoaDon>> getAllHoaDon() {
        return ResponseEntity.ok(hoaDonService.getAllHoaDon());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getHoaDonById(@PathVariable int id) {
        return hoaDonService.getHoaDonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/khachhang/{khachhangId}")
    public ResponseEntity<List<HoaDon>> getHoaDonByKhachHangId(@PathVariable int khachhangId) {
        return ResponseEntity.ok(hoaDonService.getHoaDonByKhachHangId(khachhangId));
    }
    
    @GetMapping("/canho/{canhoId}")
    public ResponseEntity<List<HoaDon>> getHoaDonByCanHoId(@PathVariable int canhoId) {
        return ResponseEntity.ok(hoaDonService.getHoaDonByCanHoId(canhoId));
    }
    
    @GetMapping("/date")
    public ResponseEntity<List<HoaDon>> getHoaDonByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(hoaDonService.getHoaDonByDateRange(startDate, endDate));
    }
    
    /**
     * Updates payment status for an invoice
     */
    @PutMapping("/{id}/payment")
    public ResponseEntity<?> updatePaymentStatus(
            @PathVariable int id,
            @RequestParam float amount) {
        try {
            HoaDon updatedHoaDon = hoaDonService.updatePaymentStatus(id, amount);
            return ResponseEntity.ok(updatedHoaDon);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}