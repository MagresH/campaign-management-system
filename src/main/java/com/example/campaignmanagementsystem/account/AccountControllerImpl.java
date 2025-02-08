package com.example.campaignmanagementsystem.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    public BigDecimal getBalanceBySellerId(UUID sellerId) {
        return accountService.getBalanceBySellerId(sellerId);
    }

    public void deposit(UUID sellerId, BigDecimal amount) {
        accountService.deposit(sellerId, amount);
    }

    public void withdraw(UUID sellerId, BigDecimal amount) {
        accountService.withdraw(sellerId, amount);
    }

    public boolean hasSufficientFunds(UUID sellerId, BigDecimal amount) {
        return accountService.hasSufficientFunds(sellerId, amount);
    }

    public Account createAccount(UUID sellerId, BigDecimal initialBalance) {
        return accountService.createAccount(sellerId, initialBalance);
    }

    public void deleteAccount(UUID sellerId) {
        accountService.deleteAccount(sellerId);
    }
}
