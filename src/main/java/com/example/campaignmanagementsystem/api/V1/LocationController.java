package com.example.campaignmanagementsystem.api.V1;

import com.example.campaignmanagementsystem.location.LocationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/locations")
@Tag(name = "Location", description = "Operations related to locations")
public interface LocationController {

    @Operation(summary = "Get all towns")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of towns retrieved")
    })
    @GetMapping("/towns")
    ResponseEntity<List<String>> getAllTowns();

    @Operation(summary = "Get location by town name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location retrieved"),
            @ApiResponse(responseCode = "404", description = "Town not found")
    })
    @GetMapping("/town")
    ResponseEntity<LocationDTO> getLocationByTown(@RequestParam String town);

    @Operation(summary = "Get location by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location retrieved"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @GetMapping("/{locationId}")
    ResponseEntity<LocationDTO> getLocationById(@PathVariable UUID locationId);

    @Operation(summary = "Create a new location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location created")
    })
    @PostMapping
    ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO);

    @Operation(summary = "Update an existing location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location updated"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @PutMapping("/{locationId}")
    ResponseEntity<LocationDTO> updateLocation(@PathVariable UUID locationId, @RequestBody LocationDTO locationDTO);

    @Operation(summary = "Delete a location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Location deleted"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @DeleteMapping("/{locationId}")
    ResponseEntity<Void> deleteLocation(@PathVariable UUID locationId);
}
