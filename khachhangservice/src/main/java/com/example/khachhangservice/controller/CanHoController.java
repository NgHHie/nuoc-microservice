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
import org.springframework.web.bind.annotation.RestController;

import com.example.khachhangservice.model.CanHo;
import com.example.khachhangservice.model.KhachHang;
import com.example.khachhangservice.service.CanHoService;
import com.example.khachhangservice.service.KhachHangService;

import jakarta.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/khachhang/canho")
public class CanHoController {

    @Autowired
    private CanHoService canHoService;
    
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping
    public List<CanHo> getAllCanHo() {
        return canHoService.getAllCanHo();
    }

    @GetMapping("/{id}")
    public Optional<CanHo> getCanHoById(@PathVariable int id) {
        return canHoService.getCanHoById(id);
    }
    
    @GetMapping("/khachhang/{khachHangId}")
    public List<CanHo> getCanHoByKhachHangId(@PathVariable int khachHangId) {
        Optional<KhachHang> khachHang = khachHangService.getKhachHangById(khachHangId);
        if (khachHang.isPresent()) {
            return khachHang.get().getCanho();
        }
        return List.of();
    }

    @PostMapping
    public CanHo createCanHo(@RequestBody CanHo canHo) {
        System.out.println(canHo);
        return canHoService.saveCanHo(canHo);
    }

    @PutMapping("/{id}")
    public CanHo updateCanHo(@PathVariable int id, @RequestBody CanHo canHo) {
        canHo.setId(id);
        return canHoService.saveCanHo(canHo);
    }

    @DeleteMapping("/{id}")
    public String deleteCanHo(@PathVariable int id) {
        canHoService.deleteCanHo(id);
        return "Căn hộ đã được xóa thành công!";
    }
}