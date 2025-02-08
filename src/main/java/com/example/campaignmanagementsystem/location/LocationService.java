package com.example.campaignmanagementsystem.location;

import java.util.List;

public interface LocationService {

    List<String> getAllTowns();

    LocationDTO getLocationByTown(String town);
}
