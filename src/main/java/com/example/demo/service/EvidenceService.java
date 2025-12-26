package com.example.demo.service;

import com.example.demo.model.Evidence;

import java.util.List;

public interface EvidenceService {

    Evidence uploadEvidence(Long claimId, Evidence evidence);

    List<Evidence> getEvidenceForClaim(Long claimId);
}
