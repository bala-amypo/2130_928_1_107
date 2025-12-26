package com.example.demo.service.impl;

import com.example.demo.model.Parcel;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.service.ParcelService;

public class ParcelServiceImpl implements ParcelService {
    private ParcelRepository parcelRepo;

    public ParcelServiceImpl(ParcelRepository parcelRepo) {
        this.parcelRepo = parcelRepo;
    }

    @Override
    public Parcel addParcel(Parcel parcel) throws Exception {
        if (parcelRepo.existsByTrackingNumber(parcel.getTrackingNumber())) {
            throw new Exception("Tracking number already exists");
        }
        return parcelRepo.save(parcel);
    }

    @Override
    public Parcel getByTrackingNumber(String trackingNumber) {
        return parcelRepo.findByTrackingNumber(trackingNumber);
    }
}