package com.example.thongkekhachhangservice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thongkekhachhangservice.dto.KhachHangRequest;
import com.example.thongkekhachhangservice.model.ThongKeKhachHang;
import com.example.thongkekhachhangservice.model.builder.AdvanceThongKe;
import com.example.thongkekhachhangservice.model.builder.BasicThongKe;
import com.example.thongkekhachhangservice.model.builder.ThongKeDirector;

@Service
public class ThongKeKhachHangService {

    @Autowired
    private HoaDonService hoaDonService;

	public ThongKeKhachHang getBasicThongKeFromKH(KhachHangRequest kh) {
		ThongKeDirector director = new ThongKeDirector();
		BasicThongKe basicBuilder = new BasicThongKe(hoaDonService);
		director.build(basicBuilder, kh);
		ThongKeKhachHang thongKe = basicBuilder.getThongKe();
		return thongKe;
	}
	
	public ThongKeKhachHang getAdvanceThongKeFromKH(KhachHangRequest kh, LocalDateTime start, LocalDateTime end) {
		ThongKeDirector director = new ThongKeDirector();
		AdvanceThongKe advanceBuilder = new AdvanceThongKe(hoaDonService);
		director.build(advanceBuilder, kh, start, end);
		ThongKeKhachHang thongKe = advanceBuilder.getThongKe();
		return thongKe;
	}
}
