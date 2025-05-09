package com.example.hopdonghoaadonservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hopdonghoaadonservice.model.HoaDon;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    
    List<HoaDon> findByKhachhangId(int khachhangId);
    
    @Query("SELECT h FROM HoaDon h JOIN h.donghonuoc d WHERE d.canhoId = :canhoId")
    List<HoaDon> findByCanhoId(int canhoId);
    
    @Query("SELECT h FROM HoaDon h JOIN h.donghonuoc d WHERE d.canhoId = :canhoId ORDER BY h.ngaylap DESC LIMIT 1")
    HoaDon findLatestByCanhoId(int canhoId);
}