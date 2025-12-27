package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidence")
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl;

    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "claim_id")
    private DamageClaim claim;

    // ======================
    // CONSTRUCTORS
    // ======================
    public Evidence() {}

    // ======================
    // GETTERS & SETTERS
    // ======================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    // 🔴 THIS SETTER WAS MISSING (CRITICAL)
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public DamageClaim getClaim() {
        return claim;
    }

    public void setClaim(DamageClaim claim) {
        this.claim = claim;
    }
}
