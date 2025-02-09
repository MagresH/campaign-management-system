package com.example.campaignmanagementsystem.location;

import com.example.campaignmanagementsystem.api.V1.LocationController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LocationControllerImpl implements LocationController {
    private final LocationService locationService;

    public ResponseEntity<List<String>> getAllTowns() {
        log.info("Fetching all towns");
        List<String> towns = locationService.getAllTowns();
        log.info("Fetched towns: {}", towns);
        return new ResponseEntity<>(towns, HttpStatus.OK);
    }

    public ResponseEntity<LocationDTO> getLocationByTown(String town) {
        log.info("Fetching location by town: {}", town);
        LocationDTO location = locationService.getLocationByTown(town);
        log.info("Fetched location: {}", location);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    public ResponseEntity<LocationDTO> getLocationById(UUID locationId) {
        log.info("Fetching location by ID: {}", locationId);
        LocationDTO location = locationService.getLocationById(locationId);
        log.info("Fetched location: {}", location);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    public ResponseEntity<LocationDTO> createLocation(LocationDTO locationDTO) {
        log.info("Creating location with details: {}", locationDTO);
        LocationDTO createdLocation = locationService.createLocation(locationDTO);
        log.info("Created location: {}", createdLocation);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    public ResponseEntity<LocationDTO> updateLocation(UUID locationId, LocationDTO locationDTO) {
        log.info("Updating location with ID: {} and details: {}", locationId, locationDTO);
        LocationDTO updatedLocation = locationService.updateLocation(locationId, locationDTO);
        log.info("Updated location: {}", updatedLocation);
        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteLocation(UUID locationId) {
        log.info("Deleting location with ID: {}", locationId);
        locationService.deleteLocation(locationId);
        log.info("Deleted location with ID: {}", locationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
