package com.example.khachhangservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@Transactional
@RequestMapping("/khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private ThongKeKhachHangClient thongKeKhachHangClient;


    @GetMapping
    public List<KhachHang> getAllKhachHang() {
        return khachHangService.getAllKhachHang();
    }

    @GetMapping("/{id}")
    public Optional<KhachHang> getKhachHangById(@PathVariable int id) {
        return khachHangService.getKhachHangById(id);
    }

    @GetMapping("/search")
    public List<KhachHang> getKhachHangByName(@RequestParam String name) {
        return khachHangService.getKhachHangByName(name);
    }

    @PostMapping
    public KhachHang createKhachHang(@RequestBody KhachHang khachHang) {
    	KhachHang kh = khachHangService.saveKhachHang(khachHang);
        thongKeKhachHangClient.updateKhachHang("create", kh);
        return kh;
    }

    @PutMapping("/{id}")
    public KhachHang updateKhachHang(@PathVariable int id, @RequestBody KhachHang khachHang) {
    	khachHang.setId(id);
    	KhachHang kh = khachHangService.saveKhachHang(khachHang);
        thongKeKhachHangClient.updateKhachHang("update", kh);
        return kh;
    }

    @DeleteMapping("/{id}")
    public String deleteKhachHang(@PathVariable int id) {
        khachHangService.deleteKhachHang(id);
        KhachHang kh = new KhachHang();
        kh.setId(id);
        thongKeKhachHangClient.updateKhachHang("delete", kh);
        return "Khách hàng đã được xóa thành công!";
    }
    
    @GetMapping("/searchbycanho/{canHoId}")
    public KhachHang getKhachHangByCanHo(@PathVariable int canHoId) {
        CanHo canHo = new CanHo();
        canHo.setId(canHoId);
        return khachHangService.getKhachHangByCanHo(canHo);
    }

    // @GetMapping("/searchbyhoadon/{hoaDonId}")
    // public KhachHang getKhachHangByHoaDon(@PathVariable int hoaDonId) {
    //     HoaDon hoaDon = new HoaDon();
    //     hoaDon.setId(hoaDonId);
    //     return khachHangService.getKhachHangByHoaDon(hoaDon);
    // }
}
