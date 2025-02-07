package com.example.campaignmanagementsystem.campaign;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CampaignMapper {
    Campaign toEntity(CreateCampaignRequest request);

    CampaignResponse toResponse(Campaign campaign);


}