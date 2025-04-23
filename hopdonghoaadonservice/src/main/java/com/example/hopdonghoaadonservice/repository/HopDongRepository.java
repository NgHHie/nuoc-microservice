package com.example.hopdonghoaadonservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hopdonghoaadonservice.model.HopDong;

@Repository
public interface HopDongRepository extends JpaRepository<HopDong, Integer> {
    
    List<HopDong> findByKhachhangId(int khachhangId);
    
    List<HopDong> findByCanhoId(int canhoId);
    
    @Query("SELECT h FROM HopDong h WHERE h.canhoId = :canhoId AND h.trangthai = 'ACTIVE'")
    Optional<HopDong> findActiveHopDongByCanhoId(int canhoId);
    
    @Query("SELECT h FROM HopDong h WHERE h.khachhangId = :khachhangId AND h.trangthai = 'ACTIVE'")
    List<HopDong> findActiveHopDongByKhachhangId(int khachhangId);
}