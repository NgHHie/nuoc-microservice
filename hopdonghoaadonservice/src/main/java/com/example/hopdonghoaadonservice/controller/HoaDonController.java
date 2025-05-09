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
@RequestMapping("/hoadon")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;
    
    @GetMapping("/khachhang/{khachhangId}")
    public ResponseEntity<List<HoaDon>> getHoaDonByKhachHangId(@PathVariable int khachhangId) {
        return ResponseEntity.ok(hoaDonService.getHoaDonByKhachHangId(khachhangId));
    }
    
}