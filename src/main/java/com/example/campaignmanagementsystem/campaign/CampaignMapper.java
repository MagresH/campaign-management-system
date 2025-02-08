package com.example.campaignmanagementsystem.campaign;

import com.example.campaignmanagementsystem.keyword.Keyword;
import com.example.campaignmanagementsystem.location.Location;
import com.example.campaignmanagementsystem.location.LocationService;
import com.example.campaignmanagementsystem.product.Product;
import com.example.campaignmanagementsystem.product.ProductService;
import com.example.campaignmanagementsystem.seller.Seller;
import com.example.campaignmanagementsystem.seller.SellerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public abstract class CampaignMapper {

    @Autowired
    protected LocationService locationService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected SellerService sellerService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "keywords", expression = "java(mapKeywords(request.keywords()))")
    @Mapping(target = "location", expression = "java(getLocationByTown(request.town()))")
    @Mapping(target = "product", expression = "java(getProductById(request.productId()))")
    @Mapping(target = "seller", expression = "java(getSellerById(request.sellerId()))")
    public abstract Campaign toEntity(CreateCampaignRequest request);

    @Mapping(target = "keywords", expression = "java(mapKeywordsToStrings(campaign.getKeywords()))")
    @Mapping(target = "town", expression = "java(getTownByLocationTown(campaign.getLocation().getTown()))")
    @Mapping(target = "productId", expression = "java(getProductId(campaign.getProduct()))")
    @Mapping(target = "sellerId", expression = "java(campaign.getSeller().getId())")
    public abstract CampaignResponse toResponse(Campaign campaign);

    protected Set<Keyword> mapKeywords(List<String> values) {
        return values.stream()
                .map(Keyword::new)
                .collect(Collectors.toSet());
    }

    protected List<String> mapKeywordsToStrings(Set<Keyword> keywords) {
        return keywords.stream()
                .map(Keyword::getText)
                .collect(Collectors.toList());
    }
    protected UUID getProductId(UUID productId) {
        return productId;
    }

    protected UUID getProductId(Product product) {
        return product.getId();
    }

    protected String getTownByLocationTown(String town) {
        return town;
    }

    protected Location getLocationByTown(String town) {
        return locationService.getLocationEntityByTown(town);
    }

    protected Product getProductById(UUID productId) {
        return productService.getProductEntityById(productId);
    }

    protected Seller getSellerById(UUID sellerId) {
        return sellerService.getSellerEntityById(sellerId);
    }
}
