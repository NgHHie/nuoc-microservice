package com.example.thongkekhachhangservice.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.thongkekhachhangservice.model.KhachHang;
import com.example.thongkekhachhangservice.model.ThongKeKhachHang;
import com.example.thongkekhachhangservice.service.KhachHangService;
import com.example.thongkekhachhangservice.service.ThongKeKhachHangService;

@RestController
@RequestMapping("/thongkekhachhang")
public class ThongKeKhachHangController {

	@Autowired
	private ThongKeKhachHangService thongKeKhachHangService;
    @Autowired
    private KhachHangService khachHangService;
	
	@GetMapping
	public List<Map<String, Object>> thongKe(
	        @RequestParam(required = false) Float start,
	        @RequestParam(required = false) Float end) {
		
        List<KhachHang> khList = khachHangService.getAllKhachHangs();
		List<Map<String, Object>> thongKeList = new ArrayList<>();
		
		for (KhachHang kh : khList) {
			ThongKeKhachHang thongKe = thongKeKhachHangService.getBasicThongKeFromKH(kh);
			if ((start == null && end != null && thongKe.getDoanhthu() <= end) ||  
			    (start != null && end == null && thongKe.getDoanhthu() >= start) ||  
			    (start != null && end != null && thongKe.getDoanhthu() >= start && thongKe.getDoanhthu() <= end) ||  
			    (start == null && end == null)) { 
				
				Map<String, Object> thongKeMap = new HashMap<>();
				thongKeMap.put("khachHang", kh);  
				thongKeMap.put("thongKe", thongKe);
				
				thongKeList.add(thongKeMap);
			}
		}
		
		return thongKeList;
	}
	
	@GetMapping("/{idkh}")
	public List<ThongKeKhachHang> thongKeKhachHang(
		@PathVariable int idkh,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
		
		// Chuyển đổi LocalDate sang LocalDateTime nếu cần
		LocalDateTime startDateTime = start != null ? start.atStartOfDay() : null;
		LocalDateTime endDateTime = end != null ? end.atTime(23, 59, 59) : null;

		System.out.println(startDateTime);
		System.out.println(endDateTime);
		
		KhachHang kh = khachHangService.getKhachHangById(idkh);    
		ThongKeKhachHang thongKeBasic = thongKeKhachHangService.getBasicThongKeFromKH(kh);
		ThongKeKhachHang thongKeAdvance = thongKeKhachHangService.getAdvanceThongKeFromKH(kh, startDateTime, endDateTime);
		
		return Arrays.asList(thongKeBasic, thongKeAdvance);
	}
}
