package com.example.campaignmanagementsystem.campaign;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateCampaignRequest {
    @NotNull(message = "Campaign ID is mandatory")
    private UUID id;

    private String name;
    private BigDecimal bidAmount;
    private BigDecimal campaignFund;
    private CampaignStatus status;
    private Integer radius;
    private String town;
    private List<String> keywords;
    private UUID productId;
    private UUID sellerId;

}
