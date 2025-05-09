package com.example.thongkekhachhangservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thongkekhachhangservice.model.HoaDon;
import com.example.thongkekhachhangservice.model.KhachHang;
import com.example.thongkekhachhangservice.repository.HoaDonRepository;

@Service
public class HoaDonService {
	@Autowired
	private HoaDonRepository hoaDonRepository;
	
	public List<HoaDon> getHoaDonList(LocalDateTime start, LocalDateTime end) {
		if (start == null && end == null) {
	        return hoaDonRepository.findAll();
	    } 
	    if (start != null && end == null) {
	        return hoaDonRepository.findByNgaylapBetween(start, LocalDateTime.now());
	    } 
	    if (start == null && end != null) {
	        return hoaDonRepository.findByNgaylapLessThanEqual(end);
	    }

	    return hoaDonRepository.findByNgaylapBetween(start, end);
	}

    public List<HoaDon> getHoaDonList(LocalDateTime start, LocalDateTime end, Integer khachhangId) {
        if (khachhangId == null) {
            return getHoaDonList(start, end);
        }

        if (start == null && end == null) {
            return hoaDonRepository.findByKhachhangId(khachhangId);
        } 
        if (start != null && end == null) {
            return hoaDonRepository.findByKhachhangIdAndNgaylapGreaterThanEqual(khachhangId, start);
        }
        if (start == null && end != null) {
            return hoaDonRepository.findByKhachhangIdAndNgaylapLessThanEqual(khachhangId, end);
        }

        return hoaDonRepository.findByKhachhangIdAndNgaylapBetween(khachhangId, start, end);
    }

    public HoaDon saveHoaDon(HoaDon hoaDon) {
        HoaDon hd = hoaDonRepository.save(hoaDon);
        return hd;
    }

}

