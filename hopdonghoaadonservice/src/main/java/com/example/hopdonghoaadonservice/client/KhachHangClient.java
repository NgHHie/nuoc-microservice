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

    public boolean checkKhachHangExists(int id) {
        try {
            return webClientBuilder.build().get()
                    .uri(khachHangServiceUrl + "/" + id)
                    .retrieve()
                    .onStatus(status -> status.equals(HttpStatus.NOT_FOUND),
                            response -> Mono.just(new RuntimeException("Khách hàng không tồn tại")))
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
    
    public boolean checkCanHoBelongsToKhachHang(int canhoId, int khachhangId) {
        try {
            return webClientBuilder.build().get()
                    .uri(khachHangServiceUrl + "/searchbycanho/" + canhoId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .map(response -> {
                        // Extract the khachhangId from the response
                        if (response instanceof java.util.Map) {
                            @SuppressWarnings("unchecked")
                            java.util.Map<String, Object> map = (java.util.Map<String, Object>) response;
                            if (map.containsKey("id")) {
                                Integer responseKhachhangId = null;
                                if (map.get("id") instanceof Integer) {
                                    responseKhachhangId = (Integer) map.get("id");
                                } else if (map.get("id") instanceof Number) {
                                    responseKhachhangId = ((Number) map.get("id")).intValue();
                                }
                                return responseKhachhangId != null && responseKhachhangId == khachhangId;
                            }
                        }
                        return false;
                    })
                    .onErrorResume(e -> Mono.just(false))
                    .block();
        } catch (Exception e) {
            return false;
        }
    }
}