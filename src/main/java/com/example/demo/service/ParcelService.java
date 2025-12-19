package com.example.demo.service;

import com.example.demo.model.Parcel;
import java.util.List;

public interface ParcelService {
    List<Parcel> getAllParcels();
    Parcel saveParcel(Parcel parcel);
}
