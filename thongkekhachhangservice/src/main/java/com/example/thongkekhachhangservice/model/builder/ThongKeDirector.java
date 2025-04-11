package com.example.thongkekhachhangservice.model.builder;

import java.time.LocalDateTime;

import com.example.thongkekhachhangservice.dto.KhachHangRequest;



public class ThongKeDirector {
	public void build(ThongKeKHBuilder builder, KhachHangRequest kh, LocalDateTime start, LocalDateTime end) {
			builder.fromKhachHang(kh)
				.setBetween(start, end)
				.buildDoanhThu()
				.buildActivity();
		
	}
	
	public void build(ThongKeKHBuilder builder, KhachHangRequest kh) {
		builder.fromKhachHang(kh).buildDoanhThu();
	}
}
