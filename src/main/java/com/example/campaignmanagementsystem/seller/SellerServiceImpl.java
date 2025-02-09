package com.example.campaignmanagementsystem.seller;

import com.example.campaignmanagementsystem.exception.SellerNotFoundException;
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
        log.info("Creating seller with details: {}", sellerDTO);
        Seller seller = sellerMapper.toEntity(sellerDTO);
        Seller savedSeller = sellerRepository.save(seller);
        log.info("Seller created: {}", savedSeller);
        return sellerMapper.toDto(savedSeller);
    }

    @Transactional
    public SellerDTO updateSeller(UUID sellerId, SellerDTO sellerDTO) {
        log.info("Updating seller with ID: {} and details: {}", sellerId, sellerDTO);
        Seller existingSeller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller not found with ID: {}", sellerId);
                    return new SellerNotFoundException("Seller not found with ID: " + sellerId);
                });

        existingSeller.setName(sellerDTO.name());

        Seller updatedSeller = sellerRepository.save(existingSeller);
        log.info("Seller updated: {}", updatedSeller);
        return sellerMapper.toDto(updatedSeller);
    }

    @Transactional(readOnly = true)
    public SellerDTO getSellerById(UUID sellerId) {
        log.info("Fetching seller with ID: {}", sellerId);
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller not found with ID: {}", sellerId);
                    return new SellerNotFoundException("Seller not found with ID: " + sellerId);
                });
        log.info("Fetched seller: {}", seller);
        return sellerMapper.toDto(seller);
    }

    @Transactional(readOnly = true)
    public Seller getSellerEntityById(UUID sellerId) {
        log.info("Fetching seller entity with ID: {}", sellerId);
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller not found with ID: {}", sellerId);
                    return new SellerNotFoundException("Seller not found with ID: " + sellerId);
                });
    }

    @Transactional
    public void deleteSeller(UUID sellerId) {
        log.info("Deleting seller with ID: {}", sellerId);
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller not found with ID: {}", sellerId);
                    return new SellerNotFoundException("Seller not found with ID: " + sellerId);
                });
        sellerRepository.delete(seller);
        log.info("Seller deleted: {}", sellerId);
    }
}
