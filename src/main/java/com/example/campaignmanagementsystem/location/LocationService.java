package com.example.campaignmanagementsystem.location;

import java.util.List;

public interface LocationService {

    List<String> getAllTowns();

    Location getLocationByTown(String town);
}
