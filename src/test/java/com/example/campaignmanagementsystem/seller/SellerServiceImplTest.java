package com.example.campaignmanagementsystem.seller;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SellerMapper sellerMapper;

    @InjectMocks
    private SellerServiceImpl sellerService;

    private UUID sellerId;
    private Seller seller;

    @BeforeEach
    public void setUp() {
        sellerId = UUID.randomUUID();
        seller = new Seller();
        seller.setId(sellerId);
        seller.setName("Test Seller");
    }

    @Test
    public void testCreateSeller_Success() {
        SellerDTO sellerDTO = new SellerDTO(null, "New Seller");

        when(sellerMapper.toEntity(sellerDTO)).thenAnswer(invocation -> {
            Seller seller = new Seller();
            seller.setName(sellerDTO.name());
            return seller;
        });

        when(sellerRepository.save(any(Seller.class))).thenAnswer(invocation -> {
            Seller savedSeller = invocation.getArgument(0);
            savedSeller.setId(UUID.randomUUID());
            return savedSeller;
        });

        when(sellerMapper.toDto(any(Seller.class))).thenAnswer(invocation -> {
            Seller seller = invocation.getArgument(0);
            return new SellerDTO(seller.getId(), seller.getName());
        });

        SellerDTO result = sellerService.createSeller(sellerDTO);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("New Seller", result.name());

        verify(sellerMapper).toEntity(sellerDTO);
        verify(sellerRepository).save(any(Seller.class));
        verify(sellerMapper).toDto(any(Seller.class));
    }

    @Test
    public void testUpdateSeller_Success() {
        SellerDTO sellerDTO = new SellerDTO(null, "Updated Seller");

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        when(sellerRepository.save(any(Seller.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(sellerMapper.toDto(any(Seller.class))).thenAnswer(invocation -> {
            Seller seller = invocation.getArgument(0);
            return new SellerDTO(seller.getId(), seller.getName());
        });

        SellerDTO result = sellerService.updateSeller(sellerId, sellerDTO);

        assertNotNull(result);
        assertEquals(sellerId, result.id());
        assertEquals("Updated Seller", result.name());

        verify(sellerRepository).findById(sellerId);
        verify(sellerRepository).save(any(Seller.class));
        verify(sellerMapper).toDto(any(Seller.class));
    }

    @Test
    public void testUpdateSeller_NotFound() {
        UUID nonExistingSellerId = UUID.randomUUID();
        SellerDTO sellerDTO = new SellerDTO(null, "Updated Seller");

        when(sellerRepository.findById(nonExistingSellerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sellerService.updateSeller(nonExistingSellerId, sellerDTO));

        verify(sellerRepository).findById(nonExistingSellerId);
        verify(sellerRepository, never()).save(any(Seller.class));
    }

    @Test
    public void testGetSellerById_Success() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        when(sellerMapper.toDto(any(Seller.class))).thenAnswer(invocation -> {
            Seller seller = invocation.getArgument(0);
            return new SellerDTO(seller.getId(), seller.getName());
        });

        SellerDTO result = sellerService.getSellerById(sellerId);

        assertNotNull(result);
        assertEquals(sellerId, result.id());
        assertEquals("Test Seller", result.name());

        verify(sellerRepository).findById(sellerId);
    }

    @Test
    public void testGetSellerById_NotFound() {
        UUID nonExistingSellerId = UUID.randomUUID();
        when(sellerRepository.findById(nonExistingSellerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sellerService.getSellerById(nonExistingSellerId));

        verify(sellerRepository).findById(nonExistingSellerId);
    }

    @Test
    public void testDeleteSeller_Success() {
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        doNothing().when(sellerRepository).delete(seller);

        sellerService.deleteSeller(sellerId);

        verify(sellerRepository).findById(sellerId);
        verify(sellerRepository).delete(seller);
    }

    @Test
    public void testDeleteSeller_NotFound() {
        UUID nonExistingSellerId = UUID.randomUUID();
        when(sellerRepository.findById(nonExistingSellerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sellerService.deleteSeller(nonExistingSellerId));

        verify(sellerRepository).findById(nonExistingSellerId);
        verify(sellerRepository, never()).delete(any(Seller.class));
    }
}
