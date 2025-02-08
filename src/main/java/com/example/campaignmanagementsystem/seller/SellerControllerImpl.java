package com.example.campaignmanagementsystem.seller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SellerControllerImpl implements SellerController {
    private final SellerService sellerService;

    public SellerDTO createSeller(SellerDTO sellerDTO) {
        return sellerService.createSeller(sellerDTO);
    }

    public SellerDTO updateSeller(UUID sellerId, SellerDTO sellerDTO) {
        return sellerService.updateSeller(sellerId, sellerDTO);
    }

    public SellerDTO getSellerById(UUID sellerId) {
        return sellerService.getSellerById(sellerId);
    }

    public void deleteSeller(UUID sellerId) {
        sellerService.deleteSeller(sellerId);
    }
}
