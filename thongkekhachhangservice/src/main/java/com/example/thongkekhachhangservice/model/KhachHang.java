package com.example.thongkekhachhangservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class KhachHang {
    @Id
    private int id;

    private String hoten;
    private String sodienthoai;
    private String email;
}
