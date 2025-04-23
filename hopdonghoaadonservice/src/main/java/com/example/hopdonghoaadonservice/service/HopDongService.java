package com.example.hopdonghoaadonservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hopdonghoaadonservice.client.KhachHangClient;
import com.example.hopdonghoaadonservice.model.HopDong;
import com.example.hopdonghoaadonservice.repository.HopDongRepository;

@Service
public class HopDongService {

    @Autowired
    private HopDongRepository hopDongRepository;
    
    @Autowired
    private KhachHangClient khachHangClient;
    
    @Autowired
    private DichVuNuocService dichVuNuocService;
    
    public List<HopDong> getAllHopDong() {
        return hopDongRepository.findAll();
    }
    
    public Optional<HopDong> getHopDongById(int id) {
        return hopDongRepository.findById(id);
    }
    
    public List<HopDong> getHopDongByKhachHangId(int khachHangId) {
        return hopDongRepository.findByKhachhangId(khachHangId);
    }
    
    public List<HopDong> getActiveHopDongByKhachHangId(int khachHangId) {
        return hopDongRepository.findActiveHopDongByKhachhangId(khachHangId);
    }
    
    public Optional<HopDong> getActiveHopDongByCanHoId(int canHoId) {
        return hopDongRepository.findActiveHopDongByCanhoId(canHoId);
    }
    
    @Transactional
    public HopDong createHopDong(HopDong hopDong) {
        // Validate khách hàng exists
        if (!khachHangClient.checkKhachHangExists(hopDong.getKhachhangId())) {
            throw new RuntimeException("Khách hàng không tồn tại");
        }
        
        // Validate căn hộ exists
        if (!khachHangClient.checkCanHoExists(hopDong.getCanhoId())) {
            throw new RuntimeException("Căn hộ không tồn tại");
        }
        
        // Check if căn hộ belongs to khách hàng
        if (!khachHangClient.checkCanHoBelongsToKhachHang(hopDong.getCanhoId(), hopDong.getKhachhangId())) {
            throw new RuntimeException("Căn hộ không thuộc về khách hàng này");
        }
        
        // Validate dichvunuoc exists
        if (hopDong.getDichvunuoc() == null || 
            !dichVuNuocService.getDichVuNuocById(hopDong.getDichvunuoc().getId()).isPresent()) {
            throw new RuntimeException("Dịch vụ nước không tồn tại");
        }
        
        // Check if there's already an active contract for this apartment
        Optional<HopDong> existingActiveContract = getActiveHopDongByCanHoId(hopDong.getCanhoId());
        if (existingActiveContract.isPresent()) {
            throw new RuntimeException("Đã có hợp đồng hoạt động cho căn hộ này");
        }
        
        // Set default values if not provided
        if (hopDong.getNgayki() == null) {
            hopDong.setNgayki(LocalDate.now());
        }
        
        if (hopDong.getTrangthai() == null || hopDong.getTrangthai().isEmpty()) {
            hopDong.setTrangthai("ACTIVE");
        }
        
        return hopDongRepository.save(hopDong);
    }
    
    @Transactional
    public HopDong updateHopDong(int id, HopDong hopDong) {
        if (!hopDongRepository.existsById(id)) {
            throw new RuntimeException("Hợp đồng không tồn tại");
        }
        
        hopDong.setId(id);
        return hopDongRepository.save(hopDong);
    }
    
    @Transactional
    public HopDong terminateHopDong(int id) {
        Optional<HopDong> optionalHopDong = hopDongRepository.findById(id);
        if (!optionalHopDong.isPresent()) {
            throw new RuntimeException("Hợp đồng không tồn tại");
        }
        
        HopDong hopDong = optionalHopDong.get();
        hopDong.setTrangthai("TERMINATED");
        
        return hopDongRepository.save(hopDong);
    }
    
    @Transactional
    public void deleteHopDong(int id) {
        if (!hopDongRepository.existsById(id)) {
            throw new RuntimeException("Hợp đồng không tồn tại");
        }
        
        hopDongRepository.deleteById(id);
    }
}