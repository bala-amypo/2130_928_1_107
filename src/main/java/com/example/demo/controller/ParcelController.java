package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Parcel;
import com.example.demo.service.ParcelService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parcels")
@Tag(name = "Parcels")
public class ParcelController {

    private final ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping
    @Operation(summary = "Add a new parcel")
    public ApiResponse addParcel(@RequestBody Parcel parcel) {
        Parcel saved = parcelService.addParcel(parcel);
        return new ApiResponse(true, "Parcel added", saved);
    }

    @GetMapping("/tracking/{trackingNumber}")
    @Operation(summary = "Get parcel by tracking number")
    public ApiResponse getByTracking(@PathVariable String trackingNumber) {
        Parcel parcel = parcelService.getByTrackingNumber(trackingNumber);
        return new ApiResponse(true, "Parcel found", parcel);
    }
}
