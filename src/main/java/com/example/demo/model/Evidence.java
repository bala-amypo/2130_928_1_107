package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl;

    @ManyToOne
    private DamageClaim claim;

    private LocalDateTime uploadedAt;

    // 🔥 REQUIRED FOR TESTS (JPA NOT CALLED)
    public Evidence() {
        this.uploadedAt = LocalDateTime.now();
    }

    @PrePersist
    public void onUpload() {
        if (this.uploadedAt == null) {
            this.uploadedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public DamageClaim getClaim() {
        return claim;
    }

    public void setClaim(DamageClaim claim) {
        this.claim = claim;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}
