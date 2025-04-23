package com.example.hopdonghoaadonservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hopdonghoaadonservice.model.DichVuNuoc;

@Repository
public interface DichVuNuocRepository extends JpaRepository<DichVuNuoc, Integer> {
}