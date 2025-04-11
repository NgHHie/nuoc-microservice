package com.example.thongkekhachhangservice.model.builder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.thongkekhachhangservice.model.HoaDon;
import com.example.thongkekhachhangservice.service.HoaDonService;

public class BasicThongKe extends ThongKeKHBuilder{

    public BasicThongKe(HoaDonService hoaDonService) {
        super(hoaDonService); 
    }
    
	@Override
	public ThongKeKHBuilder buildDoanhThu() {
        List<HoaDon> hoaDonList = hoaDonService.getHoaDonList(null, null, thongKeKh.getKh().getId());
		int tongSoDon = hoaDonList.size();
        double tongDoanhThu = hoaDonList.stream().mapToDouble(HoaDon::getTongsotien).sum();

        thongKeKh.setDoanhthu((float) tongDoanhThu);
        thongKeKh.setSodonhang(tongSoDon);
        thongKeKh.setXephang(getRankBasedOnDoanhThu(tongDoanhThu));
        return this;
	}
	
	private String getRankBasedOnDoanhThu(double doanhthu) {
        if (doanhthu > 10000000) return "VIP";
        if (doanhthu > 5000000) return "Gold";
        return "Silver";
    }

}
