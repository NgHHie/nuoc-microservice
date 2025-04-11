package com.example.thongkekhachhangservice.model.builder;

import java.time.LocalDateTime;

import com.example.thongkekhachhangservice.dto.KhachHangRequest;
import com.example.thongkekhachhangservice.model.ThongKeKhachHang;
import com.example.thongkekhachhangservice.service.HoaDonService;

public abstract class ThongKeKHBuilder {

	protected ThongKeKhachHang thongKeKh;
	protected HoaDonService hoaDonService;

    public ThongKeKHBuilder(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }
	
	public ThongKeKHBuilder setBetween(LocalDateTime start, LocalDateTime end) {
		thongKeKh.setStart(start);
		thongKeKh.setEnd(end);
		return this;
	}
	
	public ThongKeKHBuilder fromKhachHang(KhachHangRequest kh) {
		thongKeKh = new ThongKeKhachHang();
		thongKeKh.setKh(kh);
		return this;
	}
	
	public abstract ThongKeKHBuilder buildDoanhThu();
	
	public ThongKeKHBuilder buildActivity() {
		return this;
	}
	
	public ThongKeKhachHang getThongKe() {
		return thongKeKh;
	}
	
}
