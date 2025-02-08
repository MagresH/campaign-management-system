package com.example.campaignmanagementsystem.location;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public LocationDTO getLocationByTown(String town) {
        return locationRepository.findByTownIgnoreCase(town)
                .map(locationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
    }

    public List<String> getAllTowns() {
        return locationRepository.findAll()
                .stream()
                .map(Location::getTown)
                .toList();
    }
}
