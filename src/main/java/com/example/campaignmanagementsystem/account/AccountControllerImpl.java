package com.example.campaignmanagementsystem.account;

import com.example.campaignmanagementsystem.api.V1.AccountController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    public ResponseEntity<BigDecimal> getBalanceBySellerId(UUID sellerId) {
        log.info("Getting balance for seller with id: {}", sellerId);
        BigDecimal balance = accountService.getBalanceBySellerId(sellerId);
        log.info("Balance for seller with id: {} is: {}", sellerId, balance);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    public ResponseEntity<Void> deposit(UUID sellerId, BigDecimal amount) {
        log.info("Depositing {} to seller with id: {}", amount, sellerId);
        accountService.deposit(sellerId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> withdraw(UUID sellerId, BigDecimal amount) {
        log.info("Withdrawing {} from seller with id: {}", amount, sellerId);
        accountService.withdraw(sellerId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Boolean> hasSufficientFunds(UUID sellerId, BigDecimal amount) {
        log.info("Checking if seller with id: {} has sufficient funds for amount: {}", sellerId, amount);
        boolean hasFunds = accountService.hasSufficientFunds(sellerId, amount);
        log.info("Seller with id: {} has sufficient funds for amount: {}: {}", sellerId, amount, hasFunds);
        return new ResponseEntity<>(hasFunds, HttpStatus.OK);
    }

    public ResponseEntity<AccountDTO> createAccount(UUID sellerId, BigDecimal initialBalance) {
        log.info("Creating account for seller with id: {} with initial balance: {}", sellerId, initialBalance);
        AccountDTO accountDTO = accountService.createAccount(sellerId, initialBalance);
        log.info("Account created for seller with id: {} with initial balance: {}", sellerId, initialBalance);
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteAccount(UUID sellerId) {
        log.info("Deleting account for seller with id: {}", sellerId);
        accountService.deleteAccount(sellerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}