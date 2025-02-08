package com.example.campaignmanagementsystem.campaign;

import com.example.campaignmanagementsystem.keyword.Keyword;
import com.example.campaignmanagementsystem.location.Location;
import com.example.campaignmanagementsystem.location.LocationService;
import com.example.campaignmanagementsystem.product.Product;
import com.example.campaignmanagementsystem.product.ProductService;
import com.example.campaignmanagementsystem.seller.Seller;
import com.example.campaignmanagementsystem.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignMapper {
    private final LocationService locationService;
    private final ProductService productService;
    private final SellerService sellerService;

    public Campaign toEntity(CreateCampaignRequest request) {
        Campaign campaign = new Campaign();
        campaign.setKeywords(mapKeywords(request.keywords()));
        campaign.setLocation(getLocationByTown(request.town()));
        campaign.setProduct(getProductById(request.productId()));
        campaign.setSeller(getSellerById(request.sellerId()));
        return campaign;
    }

    public CampaignResponse toResponse(Campaign campaign) {
        return new CampaignResponse(
                campaign.getId(),
                campaign.getName(),
                campaign.getBidAmount(),
                campaign.getCampaignFund(),
                campaign.getStatus(),
                campaign.getRadius(),
                campaign.getLocation().getTown(),
                mapKeywordsToStrings(campaign.getKeywords()),
                campaign.getProduct().getId(),
                getSellerById(campaign.getSeller().getId()).getId()
        );
    }

    private Set<Keyword> mapKeywords(List<String> values) {
        return values.stream()
                .map(Keyword::new)
                .collect(Collectors.toSet());
    }

    private List<String> mapKeywordsToStrings(Set<Keyword> keywords) {
        return keywords.stream()
                .map(Keyword::getText)
                .collect(Collectors.toList());
    }

    private Location getLocationByTown(String town) {
        return locationService.getLocationEntityByTown(town);
    }

    private Product getProductById(UUID productId) {
        return productService.getProductEntityById(productId);
    }

    private Seller getSellerById(UUID sellerId) {
        return sellerService.getSellerEntityById(sellerId);
    }
}