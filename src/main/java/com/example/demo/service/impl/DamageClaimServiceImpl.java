public class DamageClaimServiceImpl implements DamageClaimService {

    private final ParcelRepository parcelRepository;
    private final DamageClaimRepository claimRepository;
    private final ClaimRuleRepository ruleRepository;

    public DamageClaimServiceImpl(ParcelRepository parcelRepository,
                                  DamageClaimRepository claimRepository,
                                  ClaimRuleRepository ruleRepository) {
        this.parcelRepository = parcelRepository;
        this.claimRepository = claimRepository;
        this.ruleRepository = ruleRepository;
    }

    public DamageClaim fileClaim(Long parcelId, DamageClaim claim) {
        Parcel parcel = parcelRepository.findById(parcelId)
            .orElseThrow(() -> new ResourceNotFoundException("parcel not found"));
        claim.setParcel(parcel);
        claim.setStatus("PENDING");
        return claimRepository.save(claim);
    }

    public DamageClaim evaluateClaim(Long claimId) {
        DamageClaim claim = getClaim(claimId);
        double score = 0.0;

        for (ClaimRule rule : ruleRepository.findAll()) {
            if (RuleEngineUtil.evaluate(rule, claim)) {
                score += rule.getWeight();
                claim.getAppliedRules().add(rule);
            }
        }

        claim.setScore(score);
        if (score > 0.9) claim.setStatus("APPROVED");
        else if (score == 0.0) claim.setStatus("REJECTED");

        return claimRepository.save(claim);
    }

    public DamageClaim getClaim(Long claimId) {
        return claimRepository.findById(claimId)
            .orElseThrow(() -> new ResourceNotFoundException("claim not found"));
    }
}
