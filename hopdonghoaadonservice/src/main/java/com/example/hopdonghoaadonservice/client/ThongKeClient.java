package com.example.hopdonghoaadonservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class ThongKeClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${thongkekhachhang.service.url}") 
    private String thongKeServiceUrl;

    public boolean notifyNewHoaDon(int khachhangId, float amount) {
        try {
            return webClientBuilder.build().post()
                    .uri(thongKeServiceUrl + "/notify?khachhangId=" + khachhangId + "&amount=" + amount)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorResume(e -> {
                        // Log error but don't fail
                        System.err.println("Failed to notify thongke service: " + e.getMessage());
                        return Mono.just(false);
                    })
                    .block();
        } catch (Exception e) {
            System.err.println("Failed to notify thongke service: " + e.getMessage());
            return false;
        }
    }
}