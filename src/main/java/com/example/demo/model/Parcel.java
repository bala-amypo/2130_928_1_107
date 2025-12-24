package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "parcels",
    uniqueConstraints = @UniqueConstraint(columnNames = "trackingNumber")
)
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String trackingNumber;

    private String senderName;

    private String receiverName;

    private Double weightKg;

    private java.time.LocalDateTime deliveredAt;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL)
    private List<DamageClaim> claims = new ArrayList<>();

    public Parcel() {
    }

    public Parcel(String trackingNumber, String senderName, String receiverName, Double weightKg) {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.weightKg = weightKg;
    }

    // getters & setters

    public Long getId() {
        return id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public java.time.LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(java.time.LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }
}
