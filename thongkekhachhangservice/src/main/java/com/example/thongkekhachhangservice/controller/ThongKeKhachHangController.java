package com.example.thongkekhachhangservice.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.thongkekhachhangservice.client.KhachHangClient;
import com.example.thongkekhachhangservice.dto.KhachHangRequest;
import com.example.thongkekhachhangservice.model.ThongKeKhachHang;
import com.example.thongkekhachhangservice.service.ThongKeKhachHangService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/thongkekhachhang")
public class ThongKeKhachHangController {

	@Autowired
	private ThongKeKhachHangService thongKeKhachHangService;
    @Autowired
    private KhachHangClient khachHangClient;
	
	@GetMapping
	public List<Map<String, Object>> thongKe(
	        @RequestParam(required = false) Float start,
	        @RequestParam(required = false) Float end) {
		
        List<KhachHangRequest> khList = khachHangClient.getAllKhachHang();
		List<Map<String, Object>> thongKeList = new ArrayList<>();
		
		for (KhachHangRequest kh : khList) {
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
			@RequestParam(required = false) LocalDateTime start,
			@RequestParam(required = false) LocalDateTime end) {
		
		KhachHangRequest kh = khachHangClient.getKhachHangById(idkh);	
		ThongKeKhachHang thongKeBasic = thongKeKhachHangService.getBasicThongKeFromKH(kh);
		ThongKeKhachHang thongKeAdvance = thongKeKhachHangService.getAdvanceThongKeFromKH(kh, start, end);
		
		return Arrays.asList(thongKeBasic, thongKeAdvance);
	}
}
