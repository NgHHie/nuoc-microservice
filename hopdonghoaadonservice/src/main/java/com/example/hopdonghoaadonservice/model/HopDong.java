package com.example.hopdonghoaadonservice.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class HopDong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate ngayki;
    private String trangthai;
    
    private KhachHang kh;
    private CanHo canho;
    
    @ManyToOne
    @JoinColumn(name = "dichvunuoc_id")
    private DichVuNuoc dichvunuoc;
}