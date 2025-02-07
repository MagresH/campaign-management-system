package com.example.campaignmanagementsystem.seller;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SellerMapper {
    Seller toEntity(SellerDTO sellerDto);

    SellerDTO toDto(Seller seller);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Seller partialUpdate(SellerDTO sellerDto, @MappingTarget Seller seller);
}