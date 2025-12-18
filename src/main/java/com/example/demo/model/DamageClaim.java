package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DamageClaim {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Parcel parcel;

    private String description;
    private LocalDateTime createdAt;

    private int score;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, APPROVED, REJECTED, SUSPICIOUS
    }

    // getters & setters
}
