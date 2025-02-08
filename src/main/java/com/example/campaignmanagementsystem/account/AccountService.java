package com.example.campaignmanagementsystem.account;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {

    BigDecimal getBalanceBySellerId(UUID sellerId);

    void deposit(UUID sellerId, BigDecimal amount);

    void withdraw(UUID sellerId, BigDecimal amount);

    boolean hasSufficientFunds(UUID sellerId, BigDecimal amount);

    AccountDTO createAccount(UUID sellerId, BigDecimal initialBalance);

    void deleteAccount(UUID sellerId);
}
