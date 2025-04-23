package com.example.hopdonghoaadonservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hopdonghoaadonservice.client.KhachHangClient;
import com.example.hopdonghoaadonservice.model.DongHoNuoc;
import com.example.hopdonghoaadonservice.model.HoaDon;
import com.example.hopdonghoaadonservice.model.HopDong;
import com.example.hopdonghoaadonservice.repository.DongHoNuocRepository;

@Service
public class DongHoNuocService {

    @Autowired
    private DongHoNuocRepository dongHoNuocRepository;
    
    @Autowired
    private HopDongService hopDongService;
    
    @Autowired
    private HoaDonService hoaDonService;
    
    @Autowired
    private KhachHangClient khachHangClient;
    
    public List<DongHoNuoc> getAllDongHoNuoc() {
        return dongHoNuocRepository.findAll();
    }
    
    public Optional<DongHoNuoc> getDongHoNuocById(int id) {
        return dongHoNuocRepository.findById(id);
    }
    
    public List<DongHoNuoc> getDongHoNuocByCanHoId(int canHoId) {
        return dongHoNuocRepository.findByCanhoId(canHoId);
    }
    
    public Optional<DongHoNuoc> getLatestDongHoNuocByCanHoId(int canHoId) {
        return dongHoNuocRepository.findLatestByCanhoId(canHoId);
    }
    
    @Transactional
    public DongHoNuoc createDongHoNuoc(DongHoNuoc dongHoNuoc) {
        dongHoNuoc.setNgaycapnhat(LocalDateTime.now());
        return dongHoNuocRepository.save(dongHoNuoc);
    }
    
    /**
     * Updates water meter readings and generates an invoice
     */
    @Transactional
    public HoaDon updateChiSoDongHo(int canHoId, float chisomoi) {
        // Validate căn hộ exists
        if (!khachHangClient.checkCanHoExists(canHoId)) {
            throw new RuntimeException("Căn hộ không tồn tại");
        }
        
        // Check if apartment has an active contract
        Optional<HopDong> activeContract = hopDongService.getActiveHopDongByCanHoId(canHoId);
        if (!activeContract.isPresent()) {
            throw new RuntimeException("Căn hộ chưa có hợp đồng hoạt động");
        }
        
        // Get the latest meter reading
        Optional<DongHoNuoc> latestReading = getLatestDongHoNuocByCanHoId(canHoId);
        
        float oldReading = 0f;
        if (latestReading.isPresent()) {
            oldReading = latestReading.get().getChisomoi();
        }
        
        // Validate new reading is greater than old reading
        if (chisomoi < oldReading) {
            throw new RuntimeException("Chỉ số mới không thể nhỏ hơn chỉ số cũ (" + oldReading + ")");
        }
        
        // Create new meter reading
        DongHoNuoc dongHoNuoc = DongHoNuoc.builder()
                .chisocu(oldReading)
                .chisomoi(chisomoi)
                .canhoId(canHoId)
                .ngaycapnhat(LocalDateTime.now())
                .build();
        
        DongHoNuoc savedDongHoNuoc = dongHoNuocRepository.save(dongHoNuoc);
        
        // Calculate water usage
        float waterUsage = chisomoi - oldReading;
        
        // Generate new invoice based on water usage
        HopDong hopDong = activeContract.get();
        
        // Create and save the invoice
        HoaDon hoaDon = hoaDonService.createHoaDonFromDongHoNuoc(
                savedDongHoNuoc, 
                hopDong, 
                hopDong.getKhachhangId(), 
                waterUsage);
        
        return hoaDon;
    }
    
    @Transactional
    public DongHoNuoc updateDongHoNuoc(int id, DongHoNuoc dongHoNuoc) {
        if (!dongHoNuocRepository.existsById(id)) {
            throw new RuntimeException("Đồng hồ nước không tồn tại");
        }
        
        // Kiểm tra chỉ số mới phải lớn hơn hoặc bằng chỉ số cũ
        if (dongHoNuoc.getChisomoi() < dongHoNuoc.getChisocu()) {
            throw new RuntimeException("Chỉ số mới không thể nhỏ hơn chỉ số cũ");
        }
        
        dongHoNuoc.setId(id);
        dongHoNuoc.setNgaycapnhat(LocalDateTime.now());
        return dongHoNuocRepository.save(dongHoNuoc);
    }
    
    @Transactional
    public void deleteDongHoNuoc(int id) {
        if (!dongHoNuocRepository.existsById(id)) {
            throw new RuntimeException("Đồng hồ nước không tồn tại");
        }
        
        dongHoNuocRepository.deleteById(id);
    }
}