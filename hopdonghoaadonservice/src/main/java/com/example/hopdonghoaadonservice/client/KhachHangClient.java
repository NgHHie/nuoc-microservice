package com.example.hopdonghoaadonservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Component
public class KhachHangClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${khachhang.service.url}") 
    private String khachHangServiceUrl;

    public boolean checkCanHoExists(int id) {
        try {
            return webClientBuilder.build().get()
                    .uri(khachHangServiceUrl + "/canho/" + id)
                    .retrieve()
                    .onStatus(status -> status.equals(HttpStatus.NOT_FOUND),
                            response -> Mono.just(new RuntimeException("Căn hộ không tồn tại")))
                    .bodyToMono(Object.class)
                    .map(response -> true)
                    .onErrorResume(WebClientResponseException.class, ex -> {
                        if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                            return Mono.just(false);
                        }
                        return Mono.error(ex);
                    })
                    .block();
        } catch (Exception e) {
            return false;
        }
    }
}