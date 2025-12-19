public interface DamageClaimRepository extends JpaRepository<DamageClaim, Long> {
    List<DamageClaim> findByParcel_Id(Long parcelId);
}
