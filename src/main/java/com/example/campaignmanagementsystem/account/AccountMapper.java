package com.example.campaignmanagementsystem.account;

import org.springframework.stereotype.Service;

@Service
public class AccountMapper {

    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        Account account = new Account();
        account.setId(accountDTO.id());
        account.setBalance(accountDTO.balance());
        return account;
    }

    public AccountDTO toDto(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountDTO(
                account.getId(),
                account.getBalance()
        );
    }
}