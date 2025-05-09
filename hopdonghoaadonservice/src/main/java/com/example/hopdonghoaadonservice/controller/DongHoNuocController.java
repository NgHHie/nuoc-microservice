package com.example.hopdonghoaadonservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hopdonghoaadonservice.client.ThongKeClient;
import com.example.hopdonghoaadonservice.model.DongHoNuoc;
import com.example.hopdonghoaadonservice.model.HoaDon;
import com.example.hopdonghoaadonservice.service.DongHoNuocService;

@RestController
@RequestMapping("/donghonuoc")
public class DongHoNuocController {

    @Autowired
    private DongHoNuocService dongHoNuocService;

    @Autowired
    private ThongKeClient thongKeClient;
    
    @GetMapping("/canho/{canhoId}/latest")
    public ResponseEntity<DongHoNuoc> getLatestDongHoNuocByCanHoId(@PathVariable int canhoId) {
        return dongHoNuocService.getLatestDongHoNuocByCanHoId(canhoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<DongHoNuoc> createDongHoNuoc(@RequestBody DongHoNuoc dongHoNuoc) {
        System.out.println(dongHoNuoc);
        DongHoNuoc savedDongHoNuoc = dongHoNuocService.createDongHoNuoc(dongHoNuoc);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDongHoNuoc);
    }
    
    @PostMapping("/ghisonuoc")
    public ResponseEntity<HoaDon> updateChiSoDongHo(@RequestBody DongHoNuoc dongHoNuoc) {
        try {
            HoaDon hoaDon = dongHoNuocService.updateChiSoDongHo(dongHoNuoc);
            thongKeClient.updateHoaDon("create", hoaDon);
            return ResponseEntity.status(HttpStatus.CREATED).body(hoaDon);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}