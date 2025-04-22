package com.example.thongkekhachhangservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thongkekhachhangservice.model.KhachHang;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    Optional<KhachHang> findById(int id);
}
