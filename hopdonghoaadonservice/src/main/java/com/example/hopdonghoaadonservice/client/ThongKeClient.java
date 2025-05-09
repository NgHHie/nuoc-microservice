package com.example.hopdonghoaadonservice.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.hopdonghoaadonservice.model.HoaDon;
import reactor.core.publisher.Mono;

@Component
public class ThongKeClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${thongkekhachhang.service.url}") 
    private String thongKeServiceUrl;

    public Boolean updateHoaDon(String state, HoaDon hoadon) {
    switch (state) {
        case "create":
            Map<String, Object> body = new HashMap<>();
            body.put("ngaylap", hoadon.getNgaylap());
            body.put("tongsotien", hoadon.getTongsotien());
            body.put("khachhangId", hoadon.getKhachhangId());
            body.put("hopdongId", hoadon.getHopdong().getId());
            body.put("donghonuocId", hoadon.getDonghonuoc().getId());
            body.put("trangthai", hoadon.getTrangthai());

            return webClientBuilder.build().post()
                .uri(thongKeServiceUrl)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        default:
            return false;
    }
}

}