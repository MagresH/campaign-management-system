package com.example.campaignmanagementsystem.keyword;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface KeywordMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    Keyword toEntity(KeywordDTO keywordDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    KeywordDTO toDto(Keyword keyword);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Keyword partialUpdate(KeywordDTO keywordDto, @MappingTarget Keyword keyword);
}