package com.example.khachhangservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.khachhangservice.model.CanHo;
import com.example.khachhangservice.model.KhachHang;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    // Các phương thức hiện tại
    List<KhachHang> findByHoten(String hoten);
    
    @Query("SELECT kh FROM KhachHang kh JOIN kh.canho c WHERE c = :canho")
    KhachHang findByCanHo(CanHo canho);

    // Thêm phương thức mới có hỗ trợ phân trang
    Page<KhachHang> findAll(Pageable pageable);
    
    // Tìm kiếm theo tên có phân trang
    Page<KhachHang> findByHotenContainingIgnoreCase(String hoten, Pageable pageable);
    
    // Tìm kiếm theo email có phân trang
    Page<KhachHang> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    
    // Tìm kiếm theo số điện thoại có phân trang
    Page<KhachHang> findBySodienthoaiContaining(String sodienthoai, Pageable pageable);
}