package com.example.campaignmanagementsystem.campaign;

import com.example.campaignmanagementsystem.api.V1.CampaignController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CampaignControllerImpl implements CampaignController {
    private final CampaignService campaignService;

    public ResponseEntity<CampaignResponse> createCampaign(CreateCampaignRequest request) {
        log.info("Creating campaign with request: {}", request);
        CampaignResponse response = campaignService.createCampaign(request);
        log.info("Campaign created: {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<CampaignResponse> updateCampaign(UUID campaignId, UpdateCampaignRequest request) {
        log.info("Updating campaign with ID: {}", campaignId);
        request.setId(campaignId);
        CampaignResponse response = campaignService.updateCampaign(request);
        log.info("Campaign updated: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCampaign(UUID campaignId) {
        log.info("Deleting campaign with ID: {}", campaignId);
        campaignService.deleteCampaign(campaignId);
        log.info("Campaign deleted: {}", campaignId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<CampaignResponse> getCampaignById(UUID campaignId) {
        log.info("Fetching campaign with ID: {}", campaignId);
        CampaignResponse response = campaignService.getCampaignById(campaignId);
        log.info("Fetched campaign: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Page<CampaignResponse>> getAllCampaigns(Pageable pageable) {
        log.info("Fetching all campaigns with pageable: {}", pageable);
        Page<CampaignResponse> response = campaignService.getAllCampaigns(pageable);
        log.info("Fetched all campaigns");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Page<CampaignResponse>> getCampaignsBySellerId(UUID sellerId, Pageable pageable) {
        log.info("Fetching campaigns for seller ID: {} with pageable: {}", sellerId, pageable);
        Page<CampaignResponse> response = campaignService.getCampaignsBySellerId(sellerId, pageable);
        log.info("Fetched campaigns for seller ID: {}", sellerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
