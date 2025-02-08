package com.example.campaignmanagementsystem.account;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    Account toEntity(AccountDTO accountDTO);

    AccountDTO toDto(Account account);
}