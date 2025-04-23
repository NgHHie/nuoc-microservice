package com.example.hopdonghoaadonservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hopdonghoaadonservice.model.MucLuyTien;

@Repository
public interface MucLuyTienRepository extends JpaRepository<MucLuyTien, Integer> {
    
    List<MucLuyTien> findByDichvunuocId(int dichvunuocId);
    
    List<MucLuyTien> findByDichvunuocIdOrderByDongiaAsc(int dichvunuocId);
}