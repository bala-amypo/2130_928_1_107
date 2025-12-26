package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Parcel;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.service.ParcelService;
import org.springframework.stereotype.Service;

@Service
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepo;

    public ParcelServiceImpl(ParcelRepository parcelRepo) {
        this.parcelRepo = parcelRepo;
    }

    @Override
    public Parcel addParcel(Parcel parcel) {
        if (parcelRepo.existsByTrackingNumber(parcel.getTrackingNumber())) {
            throw new BadRequestException("Error: Tracking number already exists");
        }
        return parcelRepo.save(parcel);
    }

    @Override
    public Parcel getByTrackingNumber(String trackingNumber) {
        return parcelRepo.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel not found: " + trackingNumber));
    }
}