package com.example.campaignmanagementsystem.campaign;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CampaignControllerImpl implements CampaignController {
    private final CampaignService campaignService;

    public CampaignResponse createCampaign(CreateCampaignRequest request) {
        return campaignService.createCampaign(request);
    }

    public CampaignResponse updateCampaign(UUID campaignId, UpdateCampaignRequest request) {
        request.setId(campaignId);
        return campaignService.updateCampaign(request);
    }

    public void deleteCampaign(UUID campaignId) {
        campaignService.deleteCampaign(campaignId);
    }

    public CampaignResponse getCampaignById(UUID campaignId) {
        return campaignService.getCampaignById(campaignId);
    }

    public Page<CampaignResponse> getAllCampaigns(Pageable pageable) {
        return campaignService.getAllCampaigns(pageable);
    }

    public Page<CampaignResponse> getCampaignsBySellerId(UUID sellerId, Pageable pageable) {
        return campaignService.getCampaignsBySellerId(sellerId, pageable);
    }
}
