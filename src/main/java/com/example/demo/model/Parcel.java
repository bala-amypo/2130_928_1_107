package com.example.demo.model;

import java.time.LocalDateTime;

public class Parcel {
    private Long id;
    private String trackingNumber;
    private Double weightKg;
    private String sender;
    private LocalDateTime deliveredAt;

    public Parcel() {}

    public Parcel(String trackingNumber, String sender, String dest, Double weightKg) {
        this.trackingNumber = trackingNumber;
        this.sender = sender;
        this.weightKg = weightKg;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
}