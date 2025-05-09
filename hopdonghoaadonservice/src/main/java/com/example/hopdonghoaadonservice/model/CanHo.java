package com.example.hopdonghoaadonservice.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CanHo {
    private int id;
    private transient int socanho;
    private transient  String toanha;
    private transient  KhachHang khachhang;

}