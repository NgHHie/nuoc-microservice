package com.example.hopdonghoaadonservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hopdonghoaadonservice.model.DichVuNuoc;
import com.example.hopdonghoaadonservice.model.DongHoNuoc;
import com.example.hopdonghoaadonservice.model.HoaDon;
import com.example.hopdonghoaadonservice.model.HopDong;
import com.example.hopdonghoaadonservice.model.MucLuyTien;
import com.example.hopdonghoaadonservice.repository.HoaDonRepository;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;
    
    public List<HoaDon> getHoaDonByKhachHangId(int khachHangId) {
        return hoaDonRepository.findByKhachhangId(khachHangId);
    }
    
    @Transactional
    public HoaDon createHoaDonFromDongHoNuoc(DongHoNuoc dongHoNuoc, HopDong hopDong, int khachHangId, float waterUsage) {
        DichVuNuoc dichVuNuoc = hopDong.getDichvunuoc();
        if (dichVuNuoc == null) {
            throw new RuntimeException("Hợp đồng không có dịch vụ nước");
        }
        
        List<MucLuyTien> mucLuyTiens = dichVuNuoc.getLuytien();
        if (mucLuyTiens.isEmpty()) {
            throw new RuntimeException("Chưa có mức lũy tiến cho dịch vụ nước này");
        }
        
        float totalAmount = calculateInvoiceAmount(waterUsage, mucLuyTiens);
        
        HoaDon hoaDon = HoaDon.builder()
                .ngaylap(LocalDateTime.now())
                .tongsotien(totalAmount)
                .khachhangId(khachHangId)
                .hopdong(hopDong)
                .donghonuoc(dongHoNuoc)
                .trangthai("CHUA_THANH_TOAN")
                .build();
        
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);
        
        return savedHoaDon;
    }
    
    private float calculateInvoiceAmount(float waterUsage, List<MucLuyTien> mucLuyTiens) {
        float totalAmount = 0f;
        float remainingUsage = waterUsage;
        
        for (MucLuyTien mucLuyTien : mucLuyTiens) {
            String khoangTieuThu = mucLuyTien.getKhoangtieuthu();
            System.out.println(khoangTieuThu);
            float donGia = mucLuyTien.getDongia();
            
            // Parse the consumption range (e.g., "0-10", "11-20", "21+")
            String[] parts = khoangTieuThu.split("[-+]");
            float lowerBound = Float.parseFloat(parts[0]);
            float upperBound;
            boolean isLastTier = false;
            
            if (parts.length > 1) {
                if (parts[1].endsWith("+")) {
                    upperBound = Float.MAX_VALUE;
                    isLastTier = true;
                } else {
                    upperBound = Float.parseFloat(parts[1]);
                }
            } else {
                // If only one number, assume it's a fixed tier
                upperBound = lowerBound;
            }
            
            float usageInTier;
            if (remainingUsage <= 0) {
                continue; // Skip if no remaining usage
            } else if (isLastTier) {
                // For last tier, use all remaining usage
                usageInTier = remainingUsage;
                remainingUsage = 0;
            } else {
                // For other tiers, calculate consumption in this tier
                float tierRange = upperBound - lowerBound + 1;
                if (remainingUsage <= tierRange) {
                    usageInTier = remainingUsage;
                    remainingUsage = 0;
                } else {
                    usageInTier = tierRange;
                    remainingUsage -= tierRange;
                }
            }
            
            totalAmount += usageInTier * donGia;
            System.out.println(usageInTier);
            
            if (isLastTier || remainingUsage <= 0) {
                break;
            }
        }
        
        return totalAmount;
    }
    
}