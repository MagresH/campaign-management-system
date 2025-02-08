package com.example.campaignmanagementsystem.location;

import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LocationMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "town", source = "town")
    Location toEntity(LocationDTO locationDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "town", source = "town")
    LocationDTO toDto(Location location);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Location partialUpdate(LocationDTO locationDTO, @MappingTarget Location location);
}