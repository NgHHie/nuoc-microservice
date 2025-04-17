package com.example.thongkekhachhangservice.model.builder;

import java.time.LocalDateTime;

import com.example.thongkekhachhangservice.model.KhachHang;



public class ThongKeDirector {
	public void build(ThongKeKHBuilder builder, KhachHang kh, LocalDateTime start, LocalDateTime end) {
			builder.fromKhachHang(kh)
				.setBetween(start, end)
				.buildDoanhThu()
				.buildActivity();
		
	}
	
	public void build(ThongKeKHBuilder builder, KhachHang kh) {
		builder.fromKhachHang(kh).buildDoanhThu();
	}
}
