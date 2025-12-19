package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String detail;

    public Evidence() {}
    public Evidence(String detail) { this.detail = detail; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
}
