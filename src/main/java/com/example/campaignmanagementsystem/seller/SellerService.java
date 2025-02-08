package com.example.campaignmanagementsystem.seller;

import java.util.UUID;

public interface SellerService {

    SellerDTO createSeller(SellerDTO sellerDTO);

    SellerDTO updateSeller(UUID sellerId, SellerDTO sellerDTO);

    SellerDTO getSellerById(UUID sellerId);

    Seller getSellerEntityById(UUID sellerId);

    void deleteSeller(UUID sellerId);
}
