package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidence")
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DamageClaim claim;

    private String evidenceType;
    private String fileUrl;

    private LocalDateTime uploadedAt;

    public Evidence() {}

    @PrePersist
    void onUpload() {
        uploadedAt = LocalDateTime.now();
    }

    // getters & setters
}
