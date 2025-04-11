package com.example.khachhangservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"canho"})
public class KhachHang{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String hoten;
    private String sodienthoai;
    private String email;

    @OneToMany(mappedBy = "khachhang")
    @JsonIgnore
    private List<CanHo> canho;
}

