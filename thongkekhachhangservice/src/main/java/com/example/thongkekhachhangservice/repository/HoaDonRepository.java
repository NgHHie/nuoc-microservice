package com.example.thongkekhachhangservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.thongkekhachhangservice.model.HoaDon;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer>{
	List<HoaDon> findByNgaylapBetween(LocalDateTime start, LocalDateTime end);
	List<HoaDon> findByNgaylapLessThanEqual(LocalDateTime end);

    @Query("SELECT h FROM HoaDon h WHERE h.khachhang.id = :khachhangId")
    List<HoaDon> findByKhachhangId(@Param("khachhangId") int khachhangId);
    
    @Query("SELECT h FROM HoaDon h WHERE h.khachhang.id = :khachhangId AND h.ngaylap BETWEEN :start AND :end")
    List<HoaDon> findByKhachhangIdAndNgaylapBetween(
        @Param("khachhangId") int khachhangId, 
        @Param("start") LocalDateTime start, 
        @Param("end") LocalDateTime end);
    
    @Query("SELECT h FROM HoaDon h WHERE h.khachhang.id = :khachhangId AND h.ngaylap <= :end")
    List<HoaDon> findByKhachhangIdAndNgaylapLessThanEqual(
        @Param("khachhangId") int khachhangId, 
        @Param("end") LocalDateTime end);
    
    @Query("SELECT h FROM HoaDon h WHERE h.khachhang.id = :khachhangId AND h.ngaylap >= :start")
    List<HoaDon> findByKhachhangIdAndNgaylapGreaterThanEqual(
        @Param("khachhangId") int khachhangId, 
        @Param("start") LocalDateTime start);
}