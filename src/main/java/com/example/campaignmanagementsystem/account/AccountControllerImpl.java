package com.example.campaignmanagementsystem.account;

import com.example.campaignmanagementsystem.api.V1.AccountController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    public ResponseEntity<BigDecimal> getBalanceBySellerId(UUID sellerId) {
        BigDecimal balance = accountService.getBalanceBySellerId(sellerId);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    public ResponseEntity<Void> deposit(UUID sellerId, BigDecimal amount) {
        accountService.deposit(sellerId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> withdraw(UUID sellerId, BigDecimal amount) {
        accountService.withdraw(sellerId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Boolean> hasSufficientFunds(UUID sellerId, BigDecimal amount) {
        boolean hasFunds = accountService.hasSufficientFunds(sellerId, amount);
        return new ResponseEntity<>(hasFunds, HttpStatus.OK);
    }

    public ResponseEntity<AccountDTO> createAccount(UUID sellerId, BigDecimal initialBalance) {
        AccountDTO accountDTO = accountService.createAccount(sellerId, initialBalance);
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteAccount(UUID sellerId) {
        accountService.deleteAccount(sellerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}