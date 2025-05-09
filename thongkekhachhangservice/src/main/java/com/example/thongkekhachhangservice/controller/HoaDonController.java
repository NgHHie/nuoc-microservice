package com.example.thongkekhachhangservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.thongkekhachhangservice.model.HoaDon;
import com.example.thongkekhachhangservice.model.KhachHang;
import com.example.thongkekhachhangservice.service.HoaDonService;
import com.example.thongkekhachhangservice.service.KhachHangService;

@RestController
@CrossOrigin(origins = "http://hopdonghoaadonservice")
@RequestMapping("/api/hoadon")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @PostMapping
    public Boolean createHoaDon(@RequestBody HoaDon hoaDon) {
        KhachHang kh = new KhachHang();
        kh.setId(hoaDon.getKhachhangId());
        hoaDon.setKhachhang(kh);
    	hoaDonService.saveHoaDon(hoaDon);
        return true;
    }
    
}