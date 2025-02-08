package com.example.campaignmanagementsystem.account;

import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AccountMapper {

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "balance", source = "balance")
    Account toEntity(AccountDTO accountDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "balance", source = "balance")
    AccountDTO toDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(AccountDTO accountDTO, @MappingTarget Account account);
}