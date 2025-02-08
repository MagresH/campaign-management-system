package com.example.campaignmanagementsystem.location;

import com.example.campaignmanagementsystem.api.V1.LocationController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LocationControllerImpl implements LocationController {
    private final LocationService locationService;

    public ResponseEntity<List<String>> getAllTowns() {
        List<String> towns = locationService.getAllTowns();
        return new ResponseEntity<>(towns, HttpStatus.OK);
    }

    public ResponseEntity<LocationDTO> getLocationByTown(String town) {
        LocationDTO location = locationService.getLocationByTown(town);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    public ResponseEntity<LocationDTO> getLocationById(UUID locationId) {
        LocationDTO location = locationService.getLocationById(locationId);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    public ResponseEntity<LocationDTO> createLocation(LocationDTO locationDTO) {
        LocationDTO createdLocation = locationService.createLocation(locationDTO);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    public ResponseEntity<LocationDTO> updateLocation(UUID locationId, LocationDTO locationDTO) {
        LocationDTO updatedLocation = locationService.updateLocation(locationId, locationDTO);
        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteLocation(UUID locationId) {
        locationService.deleteLocation(locationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
