public interface ClaimRuleRepository extends JpaRepository<ClaimRule, Long> {
    List<ClaimRule> findAll();
}
