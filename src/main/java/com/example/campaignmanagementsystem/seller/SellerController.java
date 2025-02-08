package com.example.campaignmanagementsystem.seller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/sellers")
@Tag(name = "Seller", description = "Operations related to sellers")
public interface SellerController {

    @Operation(summary = "Create a new seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seller created")
    })
    @PostMapping
    SellerDTO createSeller(@RequestBody SellerDTO sellerDTO);

    @Operation(summary = "Update an existing seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller updated"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    @PutMapping("/{sellerId}")
    SellerDTO updateSeller(@PathVariable UUID sellerId, @RequestBody SellerDTO sellerDTO);

    @Operation(summary = "Get seller by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller retrieved"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    @GetMapping("/{sellerId}")
    SellerDTO getSellerById(@PathVariable UUID sellerId);

    @Operation(summary = "Delete a seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Seller deleted"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    @DeleteMapping("/{sellerId}")
    void deleteSeller(@PathVariable UUID sellerId);
}
