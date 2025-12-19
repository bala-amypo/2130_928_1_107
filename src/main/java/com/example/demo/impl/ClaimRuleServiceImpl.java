public class ClaimRuleServiceImpl implements ClaimRuleService {

    private final ClaimRuleRepository ruleRepository;

    public ClaimRuleServiceImpl(ClaimRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public ClaimRule addRule(ClaimRule rule) {
        if (rule.getWeight() < 0) {
            throw new BadRequestException("weight invalid");
        }
        return ruleRepository.save(rule);
    }

    public List<ClaimRule> getAllRules() {
        return ruleRepository.findAll();
    }
}
