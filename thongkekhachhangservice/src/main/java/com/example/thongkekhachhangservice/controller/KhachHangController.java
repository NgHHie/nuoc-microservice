package com.example.thongkekhachhangservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.thongkekhachhangservice.model.KhachHang;
import com.example.thongkekhachhangservice.service.KhachHangService;

@RestController
@CrossOrigin(origins = "http://khachhangservice")
@RequestMapping("/api/khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @PostMapping
    public Boolean createKhachHang(@RequestBody KhachHang khachHang) {
    	khachHangService.saveKhachHang(khachHang);
        return true;
    }

    @PutMapping("/{id}")
    public Boolean updateKhachHang(@PathVariable int id, @RequestBody KhachHang khachHang) {
    	khachHang.setId(id);
    	khachHangService.saveKhachHang(khachHang);
        return true;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteKhachHang(@PathVariable int id) {
        khachHangService.deleteKhachHang(id);
        return true;
    }
    
}
