package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidence")
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private DamageClaim claim;

    private String evidenceType;

    private String fileUrl;

    private LocalDateTime uploadedAt;

    public Evidence() {
    }

    @PrePersist
    public void prePersist() {
        this.uploadedAt = LocalDateTime.now();
    }

    // getters & setters

    public Long getId() {
        return id;
    }

    public DamageClaim getClaim() {
        return claim;
    }

    public void setClaim(DamageClaim claim) {
        this.claim = claim;
    }

    public String getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
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
}
