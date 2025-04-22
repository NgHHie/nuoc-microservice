package com.example.thongkekhachhangservice.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.thongkekhachhangservice.model.KhachHang;

@Component
public class KhachHangClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${khachhang.service.url}") 
    private String khachHangServiceUrl;

    public List<KhachHang> getAllKhachHang() {
        return webClientBuilder.build().get()
                .uri(khachHangServiceUrl) 
                .retrieve()
                .bodyToFlux(KhachHang.class)
                .collectList()
                .block(); 
    }

    public KhachHang getKhachHangById(int id) {
        return webClientBuilder.build().get()
                .uri(khachHangServiceUrl + "/" + id)
                .retrieve()
                .bodyToMono(KhachHang.class)
                .block();
    }
}