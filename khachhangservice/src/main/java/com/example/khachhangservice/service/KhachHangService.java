package com.example.khachhangservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.khachhangservice.model.CanHo;
import com.example.khachhangservice.model.KhachHang;
import com.example.khachhangservice.repository.KhachHangRepository;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    // Các phương thức hiện tại
    public List<KhachHang> getAllKhachHang() {
        return khachHangRepository.findAll();
    }

    public Optional<KhachHang> getKhachHangById(int id) {
        return khachHangRepository.findById(id);
    }

    public List<KhachHang> getKhachHangByName(String name) {
        return khachHangRepository.findByHoten(name);
    }

    public KhachHang saveKhachHang(KhachHang khachHang) {
        return khachHangRepository.save(khachHang);
    }

    public void deleteKhachHang(int id) {
        khachHangRepository.deleteById(id);
    }
    
    public KhachHang getKhachHangByCanHo(CanHo canho) {
        return khachHangRepository.findByCanHo(canho);
    }

    public Page<KhachHang> getAllKhachHangPaged(Pageable pageable) {
        return khachHangRepository.findAll(pageable);
    }
    
    public Page<KhachHang> searchKhachHang(String searchTerm, String searchField, Pageable pageable) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return getAllKhachHangPaged(pageable);
        }
        
        switch (searchField) {
            case "hoten":
                return khachHangRepository.findByHotenContainingIgnoreCase(searchTerm, pageable);
            case "email":
                return khachHangRepository.findByEmailContainingIgnoreCase(searchTerm, pageable);
            case "sodienthoai":
                return khachHangRepository.findBySodienthoaiContaining(searchTerm, pageable);
            default:
                // Tìm kiếm mặc định theo tên
                return khachHangRepository.findByHotenContainingIgnoreCase(searchTerm, pageable);
        }
    }
}