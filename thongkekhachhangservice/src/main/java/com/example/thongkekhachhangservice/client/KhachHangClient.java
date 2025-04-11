package com.example.thongkekhachhangservice.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.thongkekhachhangservice.dto.KhachHangRequest;

@Component
public class KhachHangClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${khachhang.service.url}") 
    private String khachHangServiceUrl;

    public List<KhachHangRequest> getAllKhachHang() {
        return webClientBuilder.build().get()
                .uri(khachHangServiceUrl) 
                .retrieve()
                .bodyToFlux(KhachHangRequest.class)
                .collectList()
                .block(); 
    }

    public KhachHangRequest getKhachHangById(int id) {
        return webClientBuilder.build().get()
                .uri(khachHangServiceUrl + "/" + id)
                .retrieve()
                .bodyToMono(KhachHangRequest.class)
                .block();
    }
}