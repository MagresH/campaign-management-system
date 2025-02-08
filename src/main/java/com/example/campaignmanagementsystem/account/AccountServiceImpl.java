package com.example.campaignmanagementsystem.account;

import com.example.campaignmanagementsystem.exception.InsufficientFundsException;
import com.example.campaignmanagementsystem.seller.Seller;
import com.example.campaignmanagementsystem.seller.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final SellerRepository sellerRepository;

    @Transactional(readOnly = true)
    public boolean hasSufficientFunds(UUID sellerId, BigDecimal amount) {
        BigDecimal balance = getBalanceBySellerId(sellerId);
        return balance.compareTo(amount) >= 0;
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalanceBySellerId(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        Account account = seller.getAccount();
        if (account == null) {
            throw new IllegalStateException("Seller does not have an account");
        }
        return account.getBalance();
    }

    @Transactional
    public void withdraw(UUID sellerId, BigDecimal amount) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
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
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        Account account = seller.getAccount();
        if (account == null) {
            throw new IllegalStateException("Seller does not have an account");
        }
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        sellerRepository.save(seller);
    }
}
