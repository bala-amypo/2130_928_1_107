package com.example.demo.service.impl;

import com.example.demo.model.Parcel;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.service.ParcelService;
import org.springframework.stereotype.Service;

@Service
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;

    public ParcelServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public Parcel addParcel(Parcel parcel) {
        return parcelRepository.save(parcel); // 🔥 REQUIRED
    }

    @Override
    public Parcel getByTrackingNumber(String trackingNumber) {
        return parcelRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Parcel not found"));
    }
}
