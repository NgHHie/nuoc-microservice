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
    
    public Optional<DongHoNuoc> getLatestDongHoNuocByCanHoId(int canHoId) {
        return dongHoNuocRepository.findLatestByCanhoId(canHoId);
    }
    
    @Transactional
    public DongHoNuoc createDongHoNuoc(DongHoNuoc dongHoNuoc) {
        dongHoNuoc.setNgaycapnhat(LocalDateTime.now());
        return dongHoNuocRepository.save(dongHoNuoc);
    }

    @Transactional
    public HoaDon updateChiSoDongHo(DongHoNuoc dongHoNuoc) {
        // Validate căn hộ exists
        int donghonuocId = dongHoNuoc.getId();
        int canHoId = dongHoNuoc.getCanho().getId();
        float chisomoi = dongHoNuoc.getChisomoi();
        if (!khachHangClient.checkCanHoExists(canHoId)) {
            throw new RuntimeException("Căn hộ không tồn tại");
        }
        
        Optional<HopDong> activeContract = hopDongService.getActiveHopDongByCanHoId(canHoId);
        if (!activeContract.isPresent()) {
            throw new RuntimeException("Căn hộ chưa có hợp đồng hoạt động");
        }
        
        Optional<DongHoNuoc> latestReading = getLatestDongHoNuocByCanHoId(canHoId);
        
        float oldReading = 0f;
        if (latestReading.isPresent()) {
            oldReading = latestReading.get().getChisomoi();
        }

        if (chisomoi < oldReading) {
            throw new RuntimeException("Chỉ số mới không thể nhỏ hơn chỉ số cũ (" + oldReading + ")");
        }
        
        DongHoNuoc dongHoNuocNew = DongHoNuoc.builder()
                .id(donghonuocId)
                .chisocu(oldReading)
                .chisomoi(chisomoi)
                .canho(dongHoNuoc.getCanho())
                .ngaycapnhat(LocalDateTime.now())
                .build();
        
        DongHoNuoc savedDongHoNuoc = dongHoNuocRepository.save(dongHoNuocNew);
        
        float waterUsage = chisomoi - oldReading;
        HopDong hopDong = activeContract.get();
        HoaDon hoaDon = hoaDonService.createHoaDonFromDongHoNuoc(
                savedDongHoNuoc, 
                hopDong, 
                hopDong.getKh().getId(), 
                waterUsage);
        
        return hoaDon;
    }
}