package com.example.khachhangservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.khachhangservice.model.CanHo;
import com.example.khachhangservice.repository.CanHoRepository;

@Service
public class CanHoService {

    @Autowired
    private CanHoRepository canHoRepository;

    public List<CanHo> getAllCanHo() {
        return canHoRepository.findAll();
    }

    public Optional<CanHo> getCanHoById(int id) {
        return canHoRepository.findById(id);
    }

    public CanHo saveCanHo(CanHo canHo) {
        return canHoRepository.save(canHo);
    }

    public void deleteCanHo(int id) {
        canHoRepository.deleteById(id);
    }
}