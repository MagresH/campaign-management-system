package com.example.campaignmanagementsystem.seller;

import com.example.campaignmanagementsystem.api.V1.SellerController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SellerControllerImpl implements SellerController {
    private final SellerService sellerService;

    public ResponseEntity<SellerDTO> createSeller(SellerDTO sellerDTO) {
        SellerDTO createdSeller = sellerService.createSeller(sellerDTO);
        return new ResponseEntity<>(createdSeller, HttpStatus.CREATED);
    }

    public ResponseEntity<SellerDTO> updateSeller(UUID sellerId, SellerDTO sellerDTO) {
        SellerDTO updatedSeller = sellerService.updateSeller(sellerId, sellerDTO);
        return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
    }

    public ResponseEntity<SellerDTO> getSellerById(UUID sellerId) {
        SellerDTO seller = sellerService.getSellerById(sellerId);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSeller(UUID sellerId) {
        sellerService.deleteSeller(sellerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}