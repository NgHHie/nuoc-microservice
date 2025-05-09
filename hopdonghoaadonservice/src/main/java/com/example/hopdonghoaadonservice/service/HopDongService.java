package com.example.hopdonghoaadonservice.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hopdonghoaadonservice.model.HopDong;
import com.example.hopdonghoaadonservice.repository.HopDongRepository;

@Service
public class HopDongService {

    @Autowired
    private HopDongRepository hopDongRepository;
    
    public Optional<HopDong> getActiveHopDongByCanHoId(int canHoId) {
        return hopDongRepository.findActiveHopDongByCanhoId(canHoId);
    }
}