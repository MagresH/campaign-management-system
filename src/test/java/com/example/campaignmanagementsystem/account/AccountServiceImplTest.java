package com.example.campaignmanagementsystem.account;

import com.example.campaignmanagementsystem.exception.InsufficientFundsException;
import com.example.campaignmanagementsystem.exception.SellerNotFoundException;
import com.example.campaignmanagementsystem.seller.Seller;
import com.example.campaignmanagementsystem.seller.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private UUID sellerId;
    private Seller seller;
    private Account account;

    @BeforeEach
    public void setUp() {
        sellerId = UUID.randomUUID();
        account = new Account();
        account.setBalance(BigDecimal.valueOf(100.0));

        seller = Seller.builder()
                .id(sellerId)
                .name("Seller")
                .account(account)
                .build();
    }

    @Test
    public void testHasSufficientFunds_True() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        boolean result = accountService.hasSufficientFunds(sellerId, BigDecimal.valueOf(50.0));
        assertTrue(result);
    }

    @Test
    public void testHasSufficientFunds_False() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        boolean result = accountService.hasSufficientFunds(sellerId, BigDecimal.valueOf(150.0));
        assertFalse(result);
    }

    @Test
    public void testGetBalance_Success() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        BigDecimal balance = accountService.getBalanceBySellerId(sellerId);
        assertEquals(BigDecimal.valueOf(100.0), balance);
    }

    @Test
    public void testGetBalance_SellerNotFound() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());
        assertThrows(SellerNotFoundException.class, () -> accountService.getBalanceBySellerId(sellerId));
    }

    @Test
    public void testWithdraw_Success() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        accountService.withdraw(sellerId, BigDecimal.valueOf(50.0));
        assertEquals(BigDecimal.valueOf(50.0), account.getBalance());
        verify(sellerRepository).save(seller);
    }

    @Test
    public void testWithdraw_InsufficientFunds() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        assertThrows(InsufficientFundsException.class, () -> accountService.withdraw(sellerId, BigDecimal.valueOf(150.0)));
        verify(sellerRepository, never()).save(any());
    }

    @Test
    public void testDeposit_Success() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        accountService.deposit(sellerId, BigDecimal.valueOf(50.0));
        assertEquals(BigDecimal.valueOf(150.0), account.getBalance());
        verify(sellerRepository).save(seller);
    }

    @Test
    public void testWithdraw_SellerNotFound() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());
        assertThrows(SellerNotFoundException.class, () -> accountService.withdraw(sellerId, BigDecimal.valueOf(50.0)));
        verify(sellerRepository, never()).save(any());
    }

    @Test
    public void testDeposit_SellerNotFound() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());
        assertThrows(SellerNotFoundException.class, () -> accountService.deposit(sellerId, BigDecimal.valueOf(50.0)));
        verify(sellerRepository, never()).save(any());
    }
}
