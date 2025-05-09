package com.example.khachhangservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class CanHo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull(message = "Số căn hộ không được để trống")
    @Min(value = 1, message = "Số căn hộ phải lớn hơn 0")
    private int socanho;
    
    @NotBlank(message = "Tòa nhà không được để trống")
    private String toanha;

    @ManyToOne
    @JoinColumn(name = "khachhang_id")
    @JsonProperty(value = "khachhang", access = JsonProperty.Access.WRITE_ONLY)
    private KhachHang khachhang;

}