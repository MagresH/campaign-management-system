package com.example.campaignmanagementsystem.campaign;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CampaignResponse(
        UUID id,
        String name,
        BigDecimal bidAmount,
        BigDecimal campaignFund,
        CampaignStatus status,
        Integer radius,
        String town,
        List<String> keywords,
        UUID productId,
        UUID sellerId
) {
}