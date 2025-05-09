package com.example.hopdonghoaadonservice.model;

import java.util.List;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class KhachHang{
    private int id;
    private transient  String hoten;
    private transient  String sodienthoai;
    private transient  String email;
    private transient  List<CanHo> canho;
}