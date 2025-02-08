package com.example.campaignmanagementsystem.campaign;

import com.example.campaignmanagementsystem.keyword.Keyword;
import com.example.campaignmanagementsystem.keyword.KeywordMapper;
import com.example.campaignmanagementsystem.location.LocationMapper;
import com.example.campaignmanagementsystem.product.ProductMapper;
import com.example.campaignmanagementsystem.seller.SellerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {KeywordMapper.class, LocationMapper.class, ProductMapper.class, SellerMapper.class})
public interface CampaignMapper {

    @Mapping(target = "keywords", expression = "java(mapKeywords(request.keywords()))")
    Campaign toEntity(CreateCampaignRequest request);

    @Mapping(target = "keywords", expression = "java(mapKeywordsToStrings(campaign.getKeywords()))")
    CampaignResponse toResponse(Campaign campaign);

    default Set<Keyword> mapKeywords(List<String> values) {
        return values.stream()
                .map(Keyword::new)
                .collect(Collectors.toSet());
    }
        default List<String> mapKeywordsToStrings(Set<Keyword> keywords) {
            return keywords.stream()
                    .map(Keyword::getText)
                    .collect(Collectors.toList());
}
}

