package com.example.campaignmanagementsystem.seller;

import com.example.campaignmanagementsystem.api.V1.SellerController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SellerControllerImpl implements SellerController {
    private final SellerService sellerService;

    public ResponseEntity<SellerDTO> createSeller(SellerDTO sellerDTO) {
        log.info("Creating seller with details: {}", sellerDTO);
        SellerDTO createdSeller = sellerService.createSeller(sellerDTO);
        log.info("Seller created: {}", createdSeller);
        return new ResponseEntity<>(createdSeller, HttpStatus.CREATED);
    }

    public ResponseEntity<SellerDTO> updateSeller(UUID sellerId, SellerDTO sellerDTO) {
        log.info("Updating seller with ID: {} and details: {}", sellerId, sellerDTO);
        SellerDTO updatedSeller = sellerService.updateSeller(sellerId, sellerDTO);
        log.info("Seller updated: {}", updatedSeller);
        return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
    }

    public ResponseEntity<SellerDTO> getSellerById(UUID sellerId) {
        log.info("Fetching seller with ID: {}", sellerId);
        SellerDTO seller = sellerService.getSellerById(sellerId);
        log.info("Fetched seller: {}", seller);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSeller(UUID sellerId) {
        log.info("Deleting seller with ID: {}", sellerId);
        sellerService.deleteSeller(sellerId);
        log.info("Seller deleted: {}", sellerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
