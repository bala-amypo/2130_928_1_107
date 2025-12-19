package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DamageClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double amount;

    @Column(name = "user_id")
    private Long userId;   // <-- Add this field

    // Lombok @Data generates getters and setters automatically
}
