package com.example.campaignmanagementsystem.location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    List<String> getAllTowns();

    Optional<LocationDTO> getLocationByTown(String town);
}
