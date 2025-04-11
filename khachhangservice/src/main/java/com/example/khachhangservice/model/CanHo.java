package com.example.khachhangservice.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CanHo {
    @Id
    private int id;

    private int socanho;
    private String toanha;

    @ManyToOne
    @JoinColumn(name = "khachhang_id")
    private KhachHang khachhang;

    @OneToOne(mappedBy = "canho")
    private DongHoNuoc donghonuoc;

}