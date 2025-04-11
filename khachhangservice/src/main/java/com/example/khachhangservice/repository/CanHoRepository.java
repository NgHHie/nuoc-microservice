package com.example.khachhangservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.khachhangservice.model.CanHo;

@Repository
public interface CanHoRepository extends JpaRepository<CanHo, Integer>{

}
