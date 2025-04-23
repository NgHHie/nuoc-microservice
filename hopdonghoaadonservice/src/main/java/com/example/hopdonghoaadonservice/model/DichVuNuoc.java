package com.example.hopdonghoaadonservice.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class DichVuNuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tendichvu;
    private String donvith;

    @OneToMany(mappedBy = "dichvunuoc")
    private List<MucLuyTien> luytien;
}