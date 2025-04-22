package com.example.thongkekhachhangservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thongkekhachhangservice.model.KhachHang;
import com.example.thongkekhachhangservice.repository.KhachHangRepository;

@Service
public class KhachHangService {
    @Autowired
	private KhachHangRepository khachHangRepository;

    public List<KhachHang> getAllKhachHangs() {
        return khachHangRepository.findAll();
    }

    public KhachHang getKhachHangById(int id) {
        return khachHangRepository.findById(id).orElse(null);
    }

    public KhachHang saveKhachHang(KhachHang khachHang) {
        KhachHang kh =  khachHangRepository.save(khachHang);
        return kh;
    }

    public void deleteKhachHang(int id) {
        khachHangRepository.deleteById(id);;
    }
    
}
