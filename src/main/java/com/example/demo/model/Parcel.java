package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String parcelNumber;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getParcelNumber() { return parcelNumber; }
    public void setParcelNumber(String parcelNumber) { this.parcelNumber = parcelNumber; }
}
