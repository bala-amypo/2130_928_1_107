package com.example.demo.repository;

import com.example.demo.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    boolean existsByTrackingNumber(String trackingNumber);
    Optional<Parcel> findByTrackingNumber(String trackingNumber);
}