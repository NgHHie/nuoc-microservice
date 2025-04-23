package com.example.hopdonghoaadonservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hopdonghoaadonservice.model.HopDong;
import com.example.hopdonghoaadonservice.service.HopDongService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hopdong")
public class HopDongController {

    @Autowired
    private HopDongService hopDongService;
    
    @GetMapping
    public ResponseEntity<List<HopDong>> getAllHopDong() {
        return ResponseEntity.ok(hopDongService.getAllHopDong());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getHopDongById(@PathVariable int id) {
        return hopDongService.getHopDongById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/khachhang/{khachhangId}")
    public ResponseEntity<List<HopDong>> getHopDongByKhachHangId(@PathVariable int khachhangId) {
        return ResponseEntity.ok(hopDongService.getHopDongByKhachHangId(khachhangId));
    }
    
    @GetMapping("/khachhang/{khachhangId}/active")
    public ResponseEntity<List<HopDong>> getActiveHopDongByKhachHangId(@PathVariable int khachhangId) {
        return ResponseEntity.ok(hopDongService.getActiveHopDongByKhachHangId(khachhangId));
    }
    
    @GetMapping("/canho/{canhoId}/active")
    public ResponseEntity<?> getActiveHopDongByCanHoId(@PathVariable int canhoId) {
        return hopDongService.getActiveHopDongByCanHoId(canhoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createHopDong(@RequestBody HopDong hopDong) {
        try {
            HopDong savedHopDong = hopDongService.createHopDong(hopDong);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedHopDong);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHopDong(@PathVariable int id, @RequestBody HopDong hopDong) {
        try {
            HopDong updatedHopDong = hopDongService.updateHopDong(id, hopDong);
            return ResponseEntity.ok(updatedHopDong);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/terminate")
    public ResponseEntity<?> terminateHopDong(@PathVariable int id) {
        try {
            HopDong terminatedHopDong = hopDongService.terminateHopDong(id);
            return ResponseEntity.ok(terminatedHopDong);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHopDong(@PathVariable int id) {
        try {
            hopDongService.deleteHopDong(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}