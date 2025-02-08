package com.example.campaignmanagementsystem.seller;

import org.springframework.stereotype.Service;

@Service
public class SellerMapper {

    public Seller toEntity(SellerDTO sellerDto) {
        if (sellerDto == null) {
            return null;
        }
        Seller seller = new Seller();
        seller.setId(sellerDto.id());
        seller.setName(sellerDto.name());
        return seller;
    }

    public SellerDTO toDto(Seller seller) {
        if (seller == null) {
            return null;
        }
        return new SellerDTO(
                seller.getId(),
                seller.getName()
        );
    }

}