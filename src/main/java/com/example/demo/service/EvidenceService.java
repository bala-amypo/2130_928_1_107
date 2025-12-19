package com.example.demo.service;

import com.example.demo.model.Evidence;
import java.util.List;

public interface EvidenceService {
    List<Evidence> getAllEvidence();
    Evidence saveEvidence(Evidence evidence);
}
