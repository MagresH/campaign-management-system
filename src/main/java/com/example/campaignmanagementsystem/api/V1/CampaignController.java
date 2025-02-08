package com.example.campaignmanagementsystem.api.V1;

import com.example.campaignmanagementsystem.campaign.CampaignResponse;
import com.example.campaignmanagementsystem.campaign.CreateCampaignRequest;
import com.example.campaignmanagementsystem.campaign.UpdateCampaignRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/campaigns")
@Tag(name = "Campaign", description = "Operations related to campaigns")
public interface CampaignController {

    @Operation(summary = "Create a new campaign")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Campaign created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    CampaignResponse createCampaign(@RequestBody CreateCampaignRequest request);

    @Operation(summary = "Update an existing campaign")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campaign updated"),
            @ApiResponse(responseCode = "404", description = "Campaign not found")
    })
    @PutMapping("/{campaignId}")
    CampaignResponse updateCampaign(@PathVariable UUID campaignId, @RequestBody UpdateCampaignRequest request);

    @Operation(summary = "Delete a campaign")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Campaign deleted"),
            @ApiResponse(responseCode = "404", description = "Campaign not found")
    })
    @DeleteMapping("/{campaignId}")
    void deleteCampaign(@PathVariable UUID campaignId);

    @Operation(summary = "Get campaign by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campaign retrieved"),
            @ApiResponse(responseCode = "404", description = "Campaign not found")
    })
    @GetMapping("/{campaignId}")
    CampaignResponse getCampaignById(@PathVariable UUID campaignId);

    @Operation(summary = "Get all campaigns")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of campaigns retrieved")
    })
    @GetMapping
    Page<CampaignResponse> getAllCampaigns(Pageable pageable);

    @Operation(summary = "Get campaigns by seller ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of seller's campaigns retrieved")
    })
    @GetMapping("/seller/{sellerId}")
    Page<CampaignResponse> getCampaignsBySellerId(@PathVariable UUID sellerId, Pageable pageable);
}
