@Entity
@Table(name = "damage_claims")
public class DamageClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Parcel parcel;

    private String claimDescription;

    private LocalDateTime filedAt;

    private String status = "PENDING";

    private Double score = 0.0;

    @ManyToMany
    private Set<ClaimRule> appliedRules = new HashSet<>();

    public DamageClaim() {}

    @PrePersist
    public void onCreate() {
        this.filedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {   // REQUIRED by tests
        this.id = id;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public Double getScore() {
        return score;
    }

    public String getClaimDescription() {
        return claimDescription;
    }
}
