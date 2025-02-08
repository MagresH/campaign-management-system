package com.example.campaignmanagementsystem.seller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SellerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "products", ignore = true)
    Seller toEntity(SellerDTO sellerDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SellerDTO toDto(Seller seller);

}