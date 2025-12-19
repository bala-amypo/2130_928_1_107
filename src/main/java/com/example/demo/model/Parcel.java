package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "parcels", uniqueConstraints = @UniqueConstraint(columnNames = "trackingNumber"))
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingNumber;
    private String senderName;
    private String receiverName;
    private Double weightKg;

    private LocalDateTime deliveredAt;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL)
    private List<DamageClaim> claims = new ArrayList<>();

    public Parcel() {}

    public Parcel(String trackingNumber, String senderName, String receiverName, Double weightKg) {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.weightKg = weightKg;
    }

    // getters & setters
}
