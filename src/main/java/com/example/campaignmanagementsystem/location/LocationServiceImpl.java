package com.example.campaignmanagementsystem.location;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationDTO getLocationByTown(String town) {
        log.info("Fetching location by town: {}", town);
        return locationRepository.findByTownIgnoreCase(town)
                .map(locationMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Location not found for town: {}", town);
                    return new EntityNotFoundException("Location not found");
                });
    }

    public Location getLocationEntityByTown(String town) {
        log.info("Fetching location entity by town: {}", town);
        return locationRepository.findByTownIgnoreCase(town)
                .orElseThrow(() -> {
                    log.error("Location entity not found for town: {}", town);
                    return new EntityNotFoundException("Location not found");
                });
    }

    public List<String> getAllTowns() {
        log.info("Fetching all towns");
        List<String> towns = locationRepository.findAll()
                .stream()
                .map(Location::getTown)
                .toList();
        log.info("Fetched towns: {}", towns);
        return towns;
    }

    public LocationDTO getLocationById(UUID locationId) {
        log.info("Fetching location by ID: {}", locationId);
        return locationRepository.findById(locationId)
                .map(locationMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Location not found with ID: {}", locationId);
                    return new EntityNotFoundException("Location not found with ID: " + locationId);
                });
    }

    public LocationDTO createLocation(LocationDTO locationDTO) {
        log.info("Creating location with details: {}", locationDTO);
        if (locationRepository.findByTownIgnoreCase(locationDTO.town()).isPresent()) {
            log.error("Location with town {} already exists", locationDTO.town());
            throw new IllegalArgumentException("Location with town " + locationDTO.town() + " already exists.");
        }
        Location location = locationMapper.toEntity(locationDTO);
        Location savedLocation = locationRepository.save(location);
        log.info("Created location: {}", savedLocation);
        return locationMapper.toDto(savedLocation);
    }

    public LocationDTO updateLocation(UUID locationId, LocationDTO locationDTO) {
        log.info("Updating location with ID: {} and details: {}", locationId, locationDTO);
        Location existingLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> {
                    log.error("Location not found with ID: {}", locationId);
                    return new EntityNotFoundException("Location not found with ID: " + locationId);
                });

        if (locationDTO.town() != null && !locationDTO.town().isBlank()) {
            existingLocation.setTown(locationDTO.town());
        }

        Location updatedLocation = locationRepository.save(existingLocation);
        log.info("Updated location: {}", updatedLocation);
        return locationMapper.toDto(updatedLocation);
    }

    public void deleteLocation(UUID locationId) {
        log.info("Deleting location with ID: {}", locationId);
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> {
                    log.error("Location not found with ID: {}", locationId);
                    return new EntityNotFoundException("Location not found with ID: " + locationId);
                });
        locationRepository.delete(location);
        log.info("Deleted location with ID: {}", locationId);
    }
}
