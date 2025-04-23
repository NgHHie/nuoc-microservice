package com.example.hopdonghoaadonservice.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime ngaylap;
    private float tongsotien;
    private float sotiendathanhtoan;
    private String trangthai;
    
    // Chi tiết về lượng nước sử dụng
    private float chisomoi;
    private float chisocu;
    private float luongnuocsd;

    private int khachhangId;
    
    @ManyToOne
    @JoinColumn(name = "hopdong_id")
    @JsonIgnore
    private HopDong hopdong;
    
    @ManyToOne
    @JoinColumn(name = "donghonuoc_id")
    @JsonIgnore
    private DongHoNuoc donghonuoc;
}