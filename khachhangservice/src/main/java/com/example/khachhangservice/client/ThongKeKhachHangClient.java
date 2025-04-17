package com.example.khachhangservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.khachhangservice.model.KhachHang;

@Component
public class ThongKeKhachHangClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${thongkekhachhang.service.url}") 
    private String thongKeKhachHangServiceUrl;

    public Boolean updateKhachHang (String state, KhachHang kh) {
        switch (state) {
            case "create":
                return webClientBuilder.build().post()
                    .uri(thongKeKhachHangServiceUrl)
                    .bodyValue(kh)  
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            
            case "update":
                return webClientBuilder.build().put()
                    .uri(thongKeKhachHangServiceUrl + "/" + kh.getId())
                    .bodyValue(kh)  
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        
            case "delete":
                return webClientBuilder.build().delete()
                    .uri(thongKeKhachHangServiceUrl + "/" + kh.getId())
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
                    
            default:
                return false;
        }
    }
}
