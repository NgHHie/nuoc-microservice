package com.example.hopdonghoaadonservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hopdonghoaadonservice.model.DongHoNuoc;

@Repository
public interface DongHoNuocRepository extends JpaRepository<DongHoNuoc, Integer> {
    
    List<DongHoNuoc> findByCanhoId(int canhoId);
    
    @Query("SELECT d FROM DongHoNuoc d WHERE d.canhoId = :canhoId ORDER BY d.ngaycapnhat DESC LIMIT 1")
    Optional<DongHoNuoc> findLatestByCanhoId(int canhoId);
}