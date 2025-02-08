package com.example.campaignmanagementsystem.location;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    Location toEntity(LocationDTO locationDTO);

    LocationDTO toDto(Location location);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Location partialUpdate(LocationDTO locationDTO, @MappingTarget Location location);
}