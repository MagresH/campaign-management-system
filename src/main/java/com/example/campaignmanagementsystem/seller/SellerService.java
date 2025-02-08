package com.example.campaignmanagementsystem.seller;

import java.util.Optional;
import java.util.UUID;

public interface SellerService {

    SellerDTO createSeller(SellerDTO sellerDTO);

    SellerDTO updateSeller(UUID sellerId, SellerDTO sellerDTO);

    Optional<SellerDTO> getSellerById(UUID sellerId);

    void deleteSeller(UUID sellerId);
}
