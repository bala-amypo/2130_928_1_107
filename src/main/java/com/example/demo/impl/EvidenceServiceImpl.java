public class EvidenceServiceImpl implements EvidenceService {

    private final EvidenceRepository evidenceRepository;
    private final DamageClaimRepository claimRepository;

    public EvidenceServiceImpl(EvidenceRepository evidenceRepository,
                               DamageClaimRepository claimRepository) {
        this.evidenceRepository = evidenceRepository;
        this.claimRepository = claimRepository;
    }

    public Evidence uploadEvidence(Long claimId, Evidence evidence) {
        DamageClaim claim = claimRepository.findById(claimId)
            .orElseThrow(() -> new ResourceNotFoundException("claim not found"));
        evidence.setClaim(claim);
        return evidenceRepository.save(evidence);
    }

    public List<Evidence> getEvidenceForClaim(Long claimId) {
        return evidenceRepository.findByClaim_Id(claimId);
    }
}
