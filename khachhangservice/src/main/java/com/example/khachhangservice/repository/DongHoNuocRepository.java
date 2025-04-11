package com.example.khachhangservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.khachhangservice.model.DongHoNuoc;

@Repository
public interface DongHoNuocRepository extends JpaRepository<DongHoNuoc, Integer>{
	
	
}

