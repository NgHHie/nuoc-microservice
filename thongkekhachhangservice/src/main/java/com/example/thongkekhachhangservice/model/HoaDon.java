package com.example.thongkekhachhangservice.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime ngaylap;
    private float tongsotien;
    private String trangthai;

    @ManyToOne
    @JoinColumn(name = "khachhang_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KhachHang khachhang;

    @Column(name = "khachhang_id", insertable = false, updatable = false)
    private int khachhangId;
    private int hopdongId;
    private int donghonuocId;
    
}