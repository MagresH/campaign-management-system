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
        return balance.compareTo(amount) >= 0;
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalanceBySellerId(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found"));
        Account account = seller.getAccount();
        if (account == null) {
            throw new IllegalStateException("Seller does not have an account");
        }
        return account.getBalance();
    }

    @Transactional
    public void withdraw(UUID sellerId, BigDecimal amount) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found"));
        Account account = seller.getAccount();
        if (account == null) {
            throw new IllegalStateException("Seller does not have an account");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        account.setBalance(newBalance);
        sellerRepository.save(seller);
    }

    @Transactional
    public void deposit(UUID sellerId, BigDecimal amount) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found"));
        Account account = seller.getAccount();
        if (account == null) {
            throw new IllegalStateException("Seller does not have an account");
        }
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        sellerRepository.save(seller);
    }

    @Transactional
    public AccountDTO createAccount(UUID sellerId, BigDecimal initialBalance) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found"));

        if (seller.getAccount() != null) {
            throw new IllegalStateException("Seller already has an account");
        }

        Account account = new Account();
        account.setSeller(seller);
        account.setBalance(initialBalance);
        seller.setAccount(account);

        sellerRepository.save(seller);
        return accountMapper.toDto(account);
    }

    @Transactional
    public void deleteAccount(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found"));

        if (seller.getAccount() == null) {
            throw new IllegalStateException("Seller does not have an account");
        }

        seller.setAccount(null);
        sellerRepository.save(seller);
    }
}
