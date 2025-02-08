package com.example.campaignmanagementsystem.location;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationDTO getLocationByTown(String town) {
        return locationRepository.findByTownIgnoreCase(town)
                .map(locationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
    }

    public Location getLocationEntityByTown(String town) {
        return locationRepository.findByTownIgnoreCase(town)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
    }

    public List<String> getAllTowns() {
        return locationRepository.findAll()
                .stream()
                .map(Location::getTown)
                .toList();
    }

    public LocationDTO getLocationById(UUID locationId) {
        return locationRepository.findById(locationId)
                .map(locationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + locationId));
    }

    public LocationDTO createLocation(LocationDTO locationDTO) {
        if (locationRepository.findByTownIgnoreCase(locationDTO.town()).isPresent()) {
            throw new IllegalArgumentException("Location with town " + locationDTO.town() + " already exists.");
        }
        Location location = locationMapper.toEntity(locationDTO);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.toDto(savedLocation);
    }

    public LocationDTO updateLocation(UUID locationId, LocationDTO locationDTO) {
        Location existingLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + locationId));

        if (locationDTO.town() != null && !locationDTO.town().isBlank()) {
            existingLocation.setTown(locationDTO.town());
        }

        Location updatedLocation = locationRepository.save(existingLocation);
        return locationMapper.toDto(updatedLocation);
    }

    public void deleteLocation(UUID locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + locationId));
        locationRepository.delete(location);
    }
}
