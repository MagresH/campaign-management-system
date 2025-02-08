package com.example.campaignmanagementsystem.location;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;
    private UUID locationId;

    @BeforeEach
    public void setUp() {
        locationId = UUID.randomUUID();
        location = new Location();
        location.setId(locationId);
        location.setTown("Warsaw");
    }

    @Test
    public void testGetLocationByTown_Found() {
        when(locationRepository.findByTownIgnoreCase("Warsaw")).thenReturn(Optional.of(location));
        when(locationMapper.toDto(location)).thenReturn(new LocationDTO(location.getId(), location.getTown()));
        LocationDTO result = locationService.getLocationByTown("Warsaw");
        assertEquals(result, locationMapper.toDto(location));
        assertEquals("Warsaw", result.town());
    }

    @Test
    public void testGetLocationByTown_NotFound() {
        when(locationRepository.findByTownIgnoreCase("Unknown")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            locationService.getLocationByTown("Unknown");
        });
    }

    @Test
    public void testGetAllLocations() {
        when(locationRepository.findAll()).thenReturn(Collections.singletonList(location));
        List<String> result = locationService.getAllTowns();
        assertEquals(1, result.size());
        assertEquals("Warsaw", result.getFirst());
    }

    @Test
    public void testGetLocationById_Found() {
        Location location = Location.builder()
                .id(locationId)
                .town("Warsaw")
                .build();
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));
        when(locationMapper.toDto(location)).thenReturn(new LocationDTO(location.getId(), location.getTown()));
        LocationDTO result = locationService.getLocationById(locationId);
        assertNotNull(result);
        assertEquals("Warsaw", result.town());
    }

    @Test
    public void testGetLocationById_NotFound() {
        UUID nonExistingId = UUID.randomUUID();
        when(locationRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            locationService.getLocationById(nonExistingId);
        });
    }
}
