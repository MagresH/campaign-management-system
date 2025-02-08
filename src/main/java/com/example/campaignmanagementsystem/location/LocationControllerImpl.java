package com.example.campaignmanagementsystem.location;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LocationControllerImpl implements LocationController {
    private final LocationService locationService;

    public List<String> getAllTowns() {
        return locationService.getAllTowns();
    }

    public LocationDTO getLocationByTown(String town) {
        return locationService.getLocationByTown(town);
    }

    public LocationDTO getLocationById(UUID locationId) {
        return locationService.getLocationById(locationId);
    }

    public LocationDTO createLocation(LocationDTO locationDTO) {
        return locationService.createLocation(locationDTO);
    }

    public LocationDTO updateLocation(UUID locationId, LocationDTO locationDTO) {
        return locationService.updateLocation(locationId, locationDTO);
    }

    public void deleteLocation(UUID locationId) {
        locationService.deleteLocation(locationId);
    }
}
