package com.example.khachhangservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.khachhangservice.model.CanHo;
import com.example.khachhangservice.model.KhachHang;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    List<KhachHang> findByHoten(String hoten);
    
    @Query("SELECT kh FROM KhachHang kh JOIN kh.canho c WHERE c = :canho")
    KhachHang findByCanHo(CanHo canho);

    // @Query("SELECT kh FROM KhachHang kh JOIN kh.hoadon h WHERE h = :hoadon")
    // KhachHang findByHoaDon(HoaDon hoadon);
}
