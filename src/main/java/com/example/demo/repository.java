package com.example.demo.repository;
import com.example.demo.model.*;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(String email);
    User save(User user);
    Optional<User> findByEmail(String email);
}

// ParcelRepository
package com.example.demo.repository;
import com.example.demo.model.Parcel;
import java.util.Optional;

public interface ParcelRepository {
    boolean existsByTrackingNumber(String trackingNumber);
    Parcel save(Parcel parcel);
    Parcel findByTrackingNumber(String trackingNumber); // or getByTrackingNumber
    Optional<Parcel> findById(Long id);
}

// DamageClaimRepository
package com.example.demo.repository;
import com.example.demo.model.DamageClaim;
import java.util.Optional;

public interface DamageClaimRepository {
    DamageClaim save(DamageClaim claim);
    Optional<DamageClaim> findById(Long id);
}

// ClaimRuleRepository
package com.example.demo.repository;
import com.example.demo.model.ClaimRule;
import java.util.List;

public interface ClaimRuleRepository {
    ClaimRule save(ClaimRule rule);
    List<ClaimRule> findAll();
}

// EvidenceRepository
package com.example.demo.repository;
import com.example.demo.model.Evidence;
import java.util.List;

public interface EvidenceRepository {
    Evidence save(Evidence evidence);
    List<Evidence> findByClaim_Id(Long claimId);
}