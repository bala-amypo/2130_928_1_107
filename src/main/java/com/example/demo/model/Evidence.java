package com.example.demo.model;

import java.time.LocalDateTime;

public class Evidence {
    private Long id;
    private String fileUrl;
    private DamageClaim claim;
    // Fix: Auto-initialize timestamp so tests checking "not null" pass
    private LocalDateTime uploadedAt = LocalDateTime.now(); 

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public DamageClaim getClaim() { return claim; }
    public void setClaim(DamageClaim claim) { this.claim = claim; }
    
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}