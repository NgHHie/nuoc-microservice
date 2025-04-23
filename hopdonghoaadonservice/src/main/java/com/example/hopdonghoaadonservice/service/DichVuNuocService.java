package com.example.hopdonghoaadonservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hopdonghoaadonservice.model.DichVuNuoc;
import com.example.hopdonghoaadonservice.model.MucLuyTien;
import com.example.hopdonghoaadonservice.repository.DichVuNuocRepository;
import com.example.hopdonghoaadonservice.repository.MucLuyTienRepository;

@Service
public class DichVuNuocService {

    @Autowired
    private DichVuNuocRepository dichVuNuocRepository;
    
    @Autowired
    private MucLuyTienRepository mucLuyTienRepository;
    
    public List<DichVuNuoc> getAllDichVuNuoc() {
        return dichVuNuocRepository.findAll();
    }
    
    public Optional<DichVuNuoc> getDichVuNuocById(int id) {
        return dichVuNuocRepository.findById(id);
    }
    
    @Transactional
    public DichVuNuoc createDichVuNuoc(DichVuNuoc dichVuNuoc) {
        return dichVuNuocRepository.save(dichVuNuoc);
    }
    
    @Transactional
    public DichVuNuoc updateDichVuNuoc(int id, DichVuNuoc dichVuNuoc) {
        if (!dichVuNuocRepository.existsById(id)) {
            throw new RuntimeException("Dịch vụ nước không tồn tại");
        }
        
        dichVuNuoc.setId(id);
        return dichVuNuocRepository.save(dichVuNuoc);
    }
    
    @Transactional
    public void deleteDichVuNuoc(int id) {
        if (!dichVuNuocRepository.existsById(id)) {
            throw new RuntimeException("Dịch vụ nước không tồn tại");
        }
        
        dichVuNuocRepository.deleteById(id);
    }
    
    public List<MucLuyTien> getMucLuyTienByDichVuNuocId(int dichVuNuocId) {
        return mucLuyTienRepository.findByDichvunuocIdOrderByDongiaAsc(dichVuNuocId);
    }
    
    @Transactional
    public MucLuyTien createMucLuyTien(int dichVuNuocId, MucLuyTien mucLuyTien) {
        Optional<DichVuNuoc> optionalDichVuNuoc = dichVuNuocRepository.findById(dichVuNuocId);
        if (!optionalDichVuNuoc.isPresent()) {
            throw new RuntimeException("Dịch vụ nước không tồn tại");
        }
        
        mucLuyTien.setDichvunuoc(optionalDichVuNuoc.get());
        return mucLuyTienRepository.save(mucLuyTien);
    }
    
    @Transactional
    public MucLuyTien updateMucLuyTien(int id, MucLuyTien mucLuyTien) {
        if (!mucLuyTienRepository.existsById(id)) {
            throw new RuntimeException("Mức lũy tiến không tồn tại");
        }
        
        // Keep the same DichVuNuoc
        Optional<MucLuyTien> existingMucLuyTien = mucLuyTienRepository.findById(id);
        if (existingMucLuyTien.isPresent()) {
            mucLuyTien.setDichvunuoc(existingMucLuyTien.get().getDichvunuoc());
        }
        
        mucLuyTien.setId(id);
        return mucLuyTienRepository.save(mucLuyTien);
    }
    
    @Transactional
    public void deleteMucLuyTien(int id) {
        if (!mucLuyTienRepository.existsById(id)) {
            throw new RuntimeException("Mức lũy tiến không tồn tại");
        }
        
        mucLuyTienRepository.deleteById(id);
    }
}