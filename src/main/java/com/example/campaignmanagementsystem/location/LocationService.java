package com.example.campaignmanagementsystem.location;

import java.util.List;
import java.util.UUID;

public interface LocationService {

    List<String> getAllTowns();

    LocationDTO getLocationByTown(String town);

    LocationDTO getLocationById(UUID locationId);

    LocationDTO createLocation(LocationDTO locationDTO);

    LocationDTO updateLocation(UUID locationId, LocationDTO locationDTO);

    void deleteLocation(UUID locationId);
}
