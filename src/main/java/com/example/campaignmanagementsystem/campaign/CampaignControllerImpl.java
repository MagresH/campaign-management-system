package com.example.campaignmanagementsystem.campaign;

import com.example.campaignmanagementsystem.api.V1.CampaignController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CampaignControllerImpl implements CampaignController {
    private final CampaignService campaignService;

    public ResponseEntity<CampaignResponse> createCampaign(CreateCampaignRequest request) {
        CampaignResponse response = campaignService.createCampaign(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<CampaignResponse> updateCampaign(UUID campaignId, UpdateCampaignRequest request) {
        request.setId(campaignId);
        CampaignResponse response = campaignService.updateCampaign(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCampaign(UUID campaignId) {
        campaignService.deleteCampaign(campaignId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<CampaignResponse> getCampaignById(UUID campaignId) {
        CampaignResponse response = campaignService.getCampaignById(campaignId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Page<CampaignResponse>> getAllCampaigns(Pageable pageable) {
        Page<CampaignResponse> response = campaignService.getAllCampaigns(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Page<CampaignResponse>> getCampaignsBySellerId(UUID sellerId, Pageable pageable) {
        Page<CampaignResponse> response = campaignService.getCampaignsBySellerId(sellerId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
