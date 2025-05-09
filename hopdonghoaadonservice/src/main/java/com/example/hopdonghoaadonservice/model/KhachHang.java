package com.example.hopdonghoaadonservice.model;

import java.util.List;

import lombok.Data;

@Data
public class KhachHang{
    private int id;
    private String hoten;
    private String sodienthoai;
    private String email;
    private List<CanHo> canho;
}