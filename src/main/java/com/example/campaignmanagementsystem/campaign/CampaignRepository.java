package com.example.campaignmanagementsystem.campaign;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
    Page<Campaign> findBySellerId(UUID sellerId, Pageable pageable);
}