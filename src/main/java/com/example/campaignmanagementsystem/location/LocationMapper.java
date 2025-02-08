package com.example.campaignmanagementsystem.location;

import org.springframework.stereotype.Service;

@Service
public class LocationMapper {

    public Location toEntity(LocationDTO locationDTO) {
        if (locationDTO == null) {
            return null;
        }
        Location location = new Location();
        location.setId(locationDTO.id());
        location.setTown(locationDTO.town());
        return location;
    }

    public LocationDTO toDto(Location location) {
        if (location == null) {
            return null;
        }
        return new LocationDTO(
                location.getId(),
                location.getTown()
        );
    }
}