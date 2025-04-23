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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hopdonghoaadonservice.model.DongHoNuoc;
import com.example.hopdonghoaadonservice.model.HoaDon;
import com.example.hopdonghoaadonservice.service.DongHoNuocService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/donghonuoc")
public class DongHoNuocController {

    @Autowired
    private DongHoNuocService dongHoNuocService;
    
    @GetMapping
    public ResponseEntity<List<DongHoNuoc>> getAllDongHoNuoc() {
        return ResponseEntity.ok(dongHoNuocService.getAllDongHoNuoc());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getDongHoNuocById(@PathVariable int id) {
        return dongHoNuocService.getDongHoNuocById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/canho/{canhoId}")
    public ResponseEntity<List<DongHoNuoc>> getDongHoNuocByCanHoId(@PathVariable int canhoId) {
        return ResponseEntity.ok(dongHoNuocService.getDongHoNuocByCanHoId(canhoId));
    }
    
    @GetMapping("/canho/{canhoId}/latest")
    public ResponseEntity<?> getLatestDongHoNuocByCanHoId(@PathVariable int canhoId) {
        return dongHoNuocService.getLatestDongHoNuocByCanHoId(canhoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Creates a new water meter reading
     */
    @PostMapping
    public ResponseEntity<DongHoNuoc> createDongHoNuoc(@RequestBody DongHoNuoc dongHoNuoc) {
        DongHoNuoc savedDongHoNuoc = dongHoNuocService.createDongHoNuoc(dongHoNuoc);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDongHoNuoc);
    }
    
    /**
     * Updates water meter reading and generates invoice
     */
    @PostMapping("/ghisonuoc")
    public ResponseEntity<?> updateChiSoDongHo(
            @RequestParam int canhoId,
            @RequestParam float chisomoi) {
        try {
            HoaDon hoaDon = dongHoNuocService.updateChiSoDongHo(canhoId, chisomoi);
            return ResponseEntity.status(HttpStatus.CREATED).body(hoaDon);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDongHoNuoc(@PathVariable int id, @RequestBody DongHoNuoc dongHoNuoc) {
        try {
            DongHoNuoc updatedDongHoNuoc = dongHoNuocService.updateDongHoNuoc(id, dongHoNuoc);
            return ResponseEntity.ok(updatedDongHoNuoc);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDongHoNuoc(@PathVariable int id) {
        try {
            dongHoNuocService.deleteDongHoNuoc(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}