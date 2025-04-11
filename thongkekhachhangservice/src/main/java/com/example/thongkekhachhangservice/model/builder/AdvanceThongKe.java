package com.example.thongkekhachhangservice.model.builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.example.thongkekhachhangservice.model.HoaDon;
import com.example.thongkekhachhangservice.service.HoaDonService;

public class AdvanceThongKe extends ThongKeKHBuilder{

    public AdvanceThongKe(HoaDonService hoaDonService) {
        super(hoaDonService); 
    }
    

	@Override
	public ThongKeKHBuilder buildDoanhThu() {
		LocalDateTime start = thongKeKh.getStart();
		LocalDateTime end = thongKeKh.getEnd();
		List<HoaDon> hoaDons = hoaDonService.getHoaDonList(start, end, thongKeKh.getKh().getId()).stream()
		        .filter(hd -> {
		            LocalDateTime ngayLap = hd.getNgaylap();
		            return (start == null || !ngayLap.isBefore(start)) && 
		                   (end == null || !ngayLap.isAfter(end));
		        })
		        .toList(); 

	    double tongDoanhThu = hoaDons.stream().mapToDouble(HoaDon::getTongsotien).sum();
	    int tongSoDon = hoaDons.size();

	    thongKeKh.setDoanhthu((float) tongDoanhThu);
	    thongKeKh.setSodonhang(tongSoDon);
	    return this;
	}
	
	@Override
	public ThongKeKHBuilder buildActivity() {
		List<HoaDon> hoaDonList = hoaDonService.getHoaDonList(thongKeKh.getStart(), thongKeKh.getEnd(), thongKeKh.getKh().getId());
		if (hoaDonList.isEmpty()) {
			thongKeKh.setLastOrderDate(null);
			thongKeKh.setInactiveDays(9999);
            return this;
        }

        LocalDateTime lastOrderDate = hoaDonList.stream()
                .map(HoaDon::getNgaylap)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        long inactiveDays = ChronoUnit.DAYS.between(lastOrderDate, LocalDateTime.now());

        thongKeKh.setLastOrderDate(lastOrderDate);
        thongKeKh.setInactiveDays(inactiveDays);
        return this;
	}

}
