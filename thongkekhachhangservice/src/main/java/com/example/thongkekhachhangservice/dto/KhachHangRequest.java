package com.example.thongkekhachhangservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class KhachHangRequest {
    private int id;

    private String hoten;
    private String sodienthoai;
    private String email;
}
