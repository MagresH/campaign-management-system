package com.example.campaignmanagementsystem.campaign;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateCampaignRequest(
        @NotBlank(message = "Campaign name is mandatory")
        String name,

        @NotNull(message = "Bid amount is mandatory")
        @DecimalMin(value = "0.01", message = "Bid amount must be at least 0.01")
        BigDecimal bidAmount,

        @NotNull(message = "Campaign fund is mandatory")
        @DecimalMin(value = "0.01", message = "Campaign fund must be at least 0.01")
        BigDecimal campaignFund,

        @NotNull(message = "Status is mandatory")
        CampaignStatus status,

        @NotNull(message = "Radius is mandatory")
        @Min(value = 1, message = "Radius must be at least 1 km")
        Integer radius,

        @NotBlank(message = "Town is mandatory")
        String town,

        @NotEmpty(message = "At least one keyword is required")
        List<String> keywords,

        @NotNull(message = "Product ID is mandatory")
        UUID productId,

        @NotNull(message = "Seller ID is mandatory")
        UUID sellerId
) {
}