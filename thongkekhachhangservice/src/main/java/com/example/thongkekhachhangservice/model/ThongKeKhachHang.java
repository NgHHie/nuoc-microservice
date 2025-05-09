package com.example.thongkekhachhangservice.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ThongKeKhachHang{	
	private float doanhthu;
	private int sodonhang;
	private String xephang;	
	private LocalDateTime start, end;
	private LocalDateTime lastOrderDate;
    private long inactiveDays;
    private LocalDateTime firstOrderDate;
    private int rewardPoints;
    
	@JsonProperty("kh")
    private KhachHang kh;
}

