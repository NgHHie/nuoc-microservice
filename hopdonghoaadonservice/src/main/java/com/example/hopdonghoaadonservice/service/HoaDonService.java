package com.example.hopdonghoaadonservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hopdonghoaadonservice.client.KhachHangClient;
import com.example.hopdonghoaadonservice.client.ThongKeClient;
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
    
    @Autowired
    private DichVuNuocService dichVuNuocService;
    
    @Autowired
    private KhachHangClient khachHangClient;
    
    @Autowired
    private ThongKeClient thongKeClient;
    
    public List<HoaDon> getAllHoaDon() {
        return hoaDonRepository.findAll();
    }
    
    public Optional<HoaDon> getHoaDonById(int id) {
        return hoaDonRepository.findById(id);
    }
    
    public List<HoaDon> getHoaDonByKhachHangId(int khachHangId) {
        return hoaDonRepository.findByKhachhangId(khachHangId);
    }
    
    public List<HoaDon> getHoaDonByCanHoId(int canHoId) {
        return hoaDonRepository.findByCanhoId(canHoId);
    }
    
    public List<HoaDon> getHoaDonByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return hoaDonRepository.findByNgaylapBetween(startDate, endDate);
    }
    
    /**
     * Creates a new invoice based on water meter reading
     */
    @Transactional
    public HoaDon createHoaDonFromDongHoNuoc(DongHoNuoc dongHoNuoc, HopDong hopDong, int khachHangId, float waterUsage) {
        // Get the water service details
        DichVuNuoc dichVuNuoc = hopDong.getDichvunuoc();
        if (dichVuNuoc == null) {
            throw new RuntimeException("Hợp đồng không có dịch vụ nước");
        }
        
        // Calculate the invoice amount based on the progressive rate tiers
        List<MucLuyTien> mucLuyTiens = dichVuNuocService.getMucLuyTienByDichVuNuocId(dichVuNuoc.getId());
        if (mucLuyTiens.isEmpty()) {
            throw new RuntimeException("Chưa có mức lũy tiến cho dịch vụ nước này");
        }
        
        float totalAmount = calculateInvoiceAmount(waterUsage, mucLuyTiens);
        
        // Create the invoice
        HoaDon hoaDon = HoaDon.builder()
                .ngaylap(LocalDateTime.now())
                .tongsotien(totalAmount)
                .khachhangId(khachHangId)
                .hopdong(hopDong)
                .donghonuoc(dongHoNuoc)
                .sotiendathanhtoan(0)
                .trangthai("CHUA_THANH_TOAN")
                .chisomoi(dongHoNuoc.getChisomoi())
                .chisocu(dongHoNuoc.getChisocu())
                .luongnuocsd(waterUsage)
                .build();
        
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);
        
        // Notify the thongke service about the new invoice
        // try {
        //     thongKeClient.notifyNewHoaDon(khachHangId, totalAmount);
        // } catch (Exception e) {
        //     // Log but continue - this is not critical for invoice creation
        //     System.err.println("Failed to notify thongke service: " + e.getMessage());
        // }
        
        return savedHoaDon;
    }
    
    /**
     * Calculate the invoice amount based on the water usage and rate tiers
     */
    private float calculateInvoiceAmount(float waterUsage, List<MucLuyTien> mucLuyTiens) {
        float totalAmount = 0f;
        float remainingUsage = waterUsage;
        
        for (MucLuyTien mucLuyTien : mucLuyTiens) {
            String khoangTieuThu = mucLuyTien.getKhoangtieuthu();
            float donGia = mucLuyTien.getDongia();
            
            // Parse the consumption range (e.g., "0-10", "11-20", "21+")
            String[] parts = khoangTieuThu.split("-");
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
            
            // Add to total amount
            totalAmount += usageInTier * donGia;
            
            // If this was the last tier or no more usage to calculate, break
            if (isLastTier || remainingUsage <= 0) {
                break;
            }
        }
        
        return totalAmount;
    }
    
    /**
     * Update the payment status of an invoice
     */
    @Transactional
    public HoaDon updatePaymentStatus(int id, float amountPaid) {
        Optional<HoaDon> optionalHoaDon = hoaDonRepository.findById(id);
        if (!optionalHoaDon.isPresent()) {
            throw new RuntimeException("Hóa đơn không tồn tại");
        }
        
        HoaDon hoaDon = optionalHoaDon.get();
        float currentPaid = hoaDon.getSotiendathanhtoan();
        float newTotalPaid = currentPaid + amountPaid;
        
        if (newTotalPaid > hoaDon.getTongsotien()) {
            throw new RuntimeException("Số tiền thanh toán vượt quá tổng số tiền hóa đơn");
        }
        
        hoaDon.setSotiendathanhtoan(newTotalPaid);
        
        // Update payment status
        if (newTotalPaid >= hoaDon.getTongsotien()) {
            hoaDon.setTrangthai("DA_THANH_TOAN");
        } else if (newTotalPaid > 0) {
            hoaDon.setTrangthai("THANH_TOAN_MOT_PHAN");
        }
        
        return hoaDonRepository.save(hoaDon);
    }
}