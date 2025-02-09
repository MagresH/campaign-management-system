package com.example.campaignmanagementsystem.account;

import com.example.campaignmanagementsystem.exception.InsufficientFundsException;
import com.example.campaignmanagementsystem.exception.SellerNotFoundException;
import com.example.campaignmanagementsystem.seller.Seller;
import com.example.campaignmanagementsystem.seller.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final SellerRepository sellerRepository;
    private final AccountMapper accountMapper;

    @Transactional(readOnly = true)
    public boolean hasSufficientFunds(UUID sellerId, BigDecimal amount) {
        BigDecimal balance = getBalanceBySellerId(sellerId);
        log.info("Checking if seller {} has sufficient funds: {}", sellerId, balance.compareTo(amount) >= 0);
        return balance.compareTo(amount) >= 0;
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalanceBySellerId(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller {} not found", sellerId);
                    return new SellerNotFoundException("Seller not found");
                });
        Account account = seller.getAccount();
        if (account == null) {
            log.error("Seller {} does not have an account", sellerId);
            throw new IllegalStateException("Seller does not have an account");
        }
        log.info("Balance for seller {}: {}", sellerId, account.getBalance());
        return account.getBalance();
    }

    @Transactional
    public void withdraw(UUID sellerId, BigDecimal amount) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller {} not found", sellerId);
                    return new SellerNotFoundException("Seller not found");
                });
        Account account = seller.getAccount();
        if (account == null) {
            log.error("Seller {} does not have an account", sellerId);
            throw new IllegalStateException("Seller does not have an account");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Seller {} has insufficient funds for withdrawal of {}", sellerId, amount);
            throw new InsufficientFundsException("Insufficient funds");
        }
        account.setBalance(newBalance);
        sellerRepository.save(seller);
        log.info("Withdrawal of {} for seller {} completed, new balance: {}", amount, sellerId, newBalance);
    }

    @Transactional
    public void deposit(UUID sellerId, BigDecimal amount) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller {} not found", sellerId);
                    return new SellerNotFoundException("Seller not found");
                });
        Account account = seller.getAccount();
        if (account == null) {
            log.error("Seller {} does not have an account", sellerId);
            throw new IllegalStateException("Seller does not have an account");
        }
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        sellerRepository.save(seller);
        log.info("Deposit of {} for seller {} completed, new balance: {}", amount, sellerId, newBalance);
    }

    @Transactional
    public AccountDTO createAccount(UUID sellerId, BigDecimal initialBalance) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller {} not found", sellerId);
                    return new SellerNotFoundException("Seller not found");
                });

        if (seller.getAccount() != null) {
            log.error("Seller {} already has an account", sellerId);
            throw new IllegalStateException("Seller already has an account");
        }

        Account account = new Account();
        account.setSeller(seller);
        account.setBalance(initialBalance);
        seller.setAccount(account);

        sellerRepository.save(seller);
        log.info("Account created for seller {} with initial balance {}", sellerId, initialBalance);
        return accountMapper.toDto(account);
    }

    @Transactional
    public void deleteAccount(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller {} not found", sellerId);
                    return new SellerNotFoundException("Seller not found");
                });

        if (seller.getAccount() == null) {
            log.error("Seller {} does not have an account", sellerId);
            throw new IllegalStateException("Seller does not have an account");
        }

        seller.setAccount(null);
        sellerRepository.save(seller);
        log.info("Account for seller {} has been deleted", sellerId);
    }
}
