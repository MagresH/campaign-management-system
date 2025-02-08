package com.example.campaignmanagementsystem.product;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface ProductMapper {

    Product toEntity(ProductDTO productDTO);

    ProductDTO toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Product partialUpdate(ProductDTO productDto, @MappingTarget Product product);
}