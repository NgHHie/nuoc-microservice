package com.example.thongkekhachhangservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime ngaylap;
    private float tongsotien;

    private int khachhangId;
    private int hopdongId;
    private int donghonuocId;
    private String trangthai;
}