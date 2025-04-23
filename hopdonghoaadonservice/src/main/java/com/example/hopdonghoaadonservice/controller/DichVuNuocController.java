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

import com.example.hopdonghoaadonservice.model.DichVuNuoc;
import com.example.hopdonghoaadonservice.model.MucLuyTien;
import com.example.hopdonghoaadonservice.service.DichVuNuocService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/dichvunuoc")
public class DichVuNuocController {

    @Autowired
    private DichVuNuocService dichVuNuocService;
    
    @GetMapping
    public ResponseEntity<List<DichVuNuoc>> getAllDichVuNuoc() {
        return ResponseEntity.ok(dichVuNuocService.getAllDichVuNuoc());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getDichVuNuocById(@PathVariable int id) {
        return dichVuNuocService.getDichVuNuocById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<DichVuNuoc> createDichVuNuoc(@RequestBody DichVuNuoc dichVuNuoc) {
        DichVuNuoc savedDichVuNuoc = dichVuNuocService.createDichVuNuoc(dichVuNuoc);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDichVuNuoc);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDichVuNuoc(@PathVariable int id, @RequestBody DichVuNuoc dichVuNuoc) {
        try {
            DichVuNuoc updatedDichVuNuoc = dichVuNuocService.updateDichVuNuoc(id, dichVuNuoc);
            return ResponseEntity.ok(updatedDichVuNuoc);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDichVuNuoc(@PathVariable int id) {
        try {
            dichVuNuocService.deleteDichVuNuoc(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    // Mức lũy tiến endpoints
    
    @GetMapping("/{id}/luytien")
    public ResponseEntity<List<MucLuyTien>> getMucLuyTienByDichVuNuocId(@PathVariable int id) {
        return ResponseEntity.ok(dichVuNuocService.getMucLuyTienByDichVuNuocId(id));
    }
    
    @PostMapping("/{id}/luytien")
    public ResponseEntity<?> createMucLuyTien(
            @PathVariable int id, 
            @RequestBody MucLuyTien mucLuyTien) {
        try {
            MucLuyTien savedMucLuyTien = dichVuNuocService.createMucLuyTien(id, mucLuyTien);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMucLuyTien);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    @PutMapping("/luytien/{id}")
    public ResponseEntity<?> updateMucLuyTien(
            @PathVariable int id, 
            @RequestBody MucLuyTien mucLuyTien) {
        try {
            MucLuyTien updatedMucLuyTien = dichVuNuocService.updateMucLuyTien(id, mucLuyTien);
            return ResponseEntity.ok(updatedMucLuyTien);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    @DeleteMapping("/luytien/{id}")
    public ResponseEntity<?> deleteMucLuyTien(@PathVariable int id) {
        try {
            dichVuNuocService.deleteMucLuyTien(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}