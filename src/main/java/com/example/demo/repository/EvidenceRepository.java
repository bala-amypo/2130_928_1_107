public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
    long countByClaim_Id(Long claimId);
    List<Evidence> findByClaim_Id(Long claimId);
}
