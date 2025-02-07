package com.example.campaignmanagementsystem.keyword;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface KeywordMapper {
    Keyword toEntity(KeywordDTO keywordDto);

    KeywordDTO toDto(Keyword keyword);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Keyword partialUpdate(KeywordDTO keywordDto, @MappingTarget Keyword keyword);
}