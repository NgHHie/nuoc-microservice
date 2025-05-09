package com.example.hopdonghoaadonservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class MucLuyTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String khoangtieuthu;
    private float dongia;
    
    @ManyToOne
    @JoinColumn(name = "dichvunuoc_id")
    @JsonIgnore
    private DichVuNuoc dichvunuoc;
}