package com.example.campaignmanagementsystem.seller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    @Transactional
    public SellerDTO createSeller(SellerDTO sellerDTO) {
        Seller seller = sellerMapper.toEntity(sellerDTO);
        Seller savedSeller = sellerRepository.save(seller);
        return sellerMapper.toDto(savedSeller);
    }

    @Transactional
    public SellerDTO updateSeller(UUID sellerId, SellerDTO sellerDTO) {
        Seller existingSeller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + sellerId));

        existingSeller.setName(sellerDTO.name());

        Seller updatedSeller = sellerRepository.save(existingSeller);
        return sellerMapper.toDto(updatedSeller);
    }

    @Transactional(readOnly = true)
    public SellerDTO getSellerById(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + sellerId));
        return sellerMapper.toDto(seller);
    }

    @Transactional(readOnly = true)
    public Seller getSellerEntityById(UUID sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + sellerId));
    }

    @Transactional
    public void deleteSeller(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + sellerId));
        sellerRepository.delete(seller);
    }
}
