package com.example.thongkekhachhangservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.thongkekhachhangservice.model.HoaDon;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer>{
	List<HoaDon> findByNgaylapBetween(LocalDateTime start, LocalDateTime end);
	List<HoaDon> findByNgaylapLessThanEqual(LocalDateTime end);

    List<HoaDon> findByKhachhangId(int khachhangId);
    List<HoaDon> findByKhachhangIdAndNgaylapBetween(int khachhangId, LocalDateTime start, LocalDateTime end);
    List<HoaDon> findByKhachhangIdAndNgaylapLessThanEqual(int khachhangId, LocalDateTime end);
    List<HoaDon> findByKhachhangIdAndNgaylapGreaterThanEqual(int khachhangId, LocalDateTime start);
}