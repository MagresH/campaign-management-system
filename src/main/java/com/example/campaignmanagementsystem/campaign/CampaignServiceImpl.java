package com.example.campaignmanagementsystem.campaign;

import com.example.campaignmanagementsystem.account.AccountService;
import com.example.campaignmanagementsystem.exception.InsufficientFundsException;
import com.example.campaignmanagementsystem.keyword.KeywordDTO;
import com.example.campaignmanagementsystem.keyword.KeywordMapper;
import com.example.campaignmanagementsystem.keyword.KeywordService;
import com.example.campaignmanagementsystem.location.LocationDTO;
import com.example.campaignmanagementsystem.location.LocationMapper;
import com.example.campaignmanagementsystem.location.LocationService;
import com.example.campaignmanagementsystem.product.ProductDTO;
import com.example.campaignmanagementsystem.product.ProductMapper;
import com.example.campaignmanagementsystem.product.ProductService;
import com.example.campaignmanagementsystem.seller.SellerDTO;
import com.example.campaignmanagementsystem.seller.SellerMapper;
import com.example.campaignmanagementsystem.seller.SellerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final KeywordService keywordService;
    private final AccountService accountService;
    private final ProductService productService;
    private final SellerService sellerService;
    private final LocationService locationService;
    private final KeywordMapper keywordMapper;
    private final LocationMapper locationMapper;
    private final ProductMapper productMapper;
    private final SellerMapper sellerMapper;
    private final CampaignMapper campaignMapper;

    @Transactional
    public CampaignResponse createCampaign(CreateCampaignRequest request) {
        log.info("Creating campaign with request: {}", request);
        SellerDTO seller = sellerService.getSellerById(request.sellerId());

        ProductDTO product = productService.getProductById(request.productId());

        LocationDTO location = locationService.getLocationByTown(request.town());

        BigDecimal campaignFund = request.campaignFund();

        UUID sellerId = seller.id();

        if (!accountService.hasSufficientFunds(sellerId, campaignFund)) {
            log.error("Insufficient funds for seller: {}", sellerId);
            throw new InsufficientFundsException("Insufficient funds");
        } else {
            accountService.withdraw(sellerId, campaignFund);
            log.info("Withdrawn {} from seller {}'s account", campaignFund, sellerId);
        }

        Set<KeywordDTO> keywords = request.keywords().stream()
                .map(keywordService::findOrCreateByValue)
                .collect(Collectors.toSet());

        Campaign campaign = Campaign.builder()
                .name(request.name())
                .bidAmount(request.bidAmount())
                .campaignFund(campaignFund)
                .status(request.status())
                .radius(request.radius())
                .location(locationMapper.toEntity(location))
                .keywords(keywords.stream()
                        .map(keywordMapper::toEntity)
                        .collect(Collectors.toSet()))
                .product(productMapper.toEntity(product))
                .seller(sellerMapper.toEntity(seller))
                .build();

        Campaign savedCampaign = campaignRepository.save(campaign);
        log.info("Campaign created: {}", savedCampaign);

        return campaignMapper.toResponse(savedCampaign);
    }

    @Transactional
    public CampaignResponse updateCampaign(UpdateCampaignRequest request) {
        log.info("Updating campaign with ID: {}", request.getId());
        Campaign existingCampaign = campaignRepository.findById(request.getId())
                .orElseThrow(() -> {
                    log.error("Campaign not found with ID: {}", request.getId());
                    return new EntityNotFoundException("Campaign not found");
                });

        SellerDTO seller;
        if (request.getSellerId() != null) {
            seller = sellerService.getSellerById(request.getSellerId());
            existingCampaign.setSeller(sellerMapper.toEntity(seller));
        }

        ProductDTO product;
        if (request.getProductId() != null) {
            product = productService.getProductById(request.getProductId());
            existingCampaign.setProduct(productMapper.toEntity(product));
        }

        if (request.getTown() != null && !request.getTown().isBlank()) {
            LocationDTO location = locationService.getLocationByTown(request.getTown());
            existingCampaign.setLocation(locationMapper.toEntity(location));
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            existingCampaign.setName(request.getName());
        }
        if (request.getBidAmount() != null) {
            existingCampaign.setBidAmount(request.getBidAmount());
        }
        if (request.getStatus() != null) {
            existingCampaign.setStatus(request.getStatus());
        }
        if (request.getRadius() != null) {
            existingCampaign.setRadius(request.getRadius());
        }

        if (request.getCampaignFund() != null) {
            BigDecimal difference = request.getCampaignFund().subtract(existingCampaign.getCampaignFund());
            UUID sellerId = existingCampaign.getSeller().getId();

            if (difference.compareTo(BigDecimal.ZERO) > 0) {
                if (!accountService.hasSufficientFunds(sellerId, difference)) {
                    log.error("Insufficient funds for seller: {}", sellerId);
                    throw new InsufficientFundsException("Insufficient funds");
                }
                accountService.withdraw(sellerId, difference);
                log.info("Withdrawn {} from seller {}'s account", difference, sellerId);
            } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
                accountService.deposit(sellerId, difference.abs());
                log.info("Deposited {} to seller {}'s account", difference.abs(), sellerId);
            }
            existingCampaign.setCampaignFund(request.getCampaignFund());
        }

        if (request.getKeywords() != null && !request.getKeywords().isEmpty()) {
            Set<KeywordDTO> keywords = request.getKeywords().stream()
                    .map(keywordService::findOrCreateByValue)
                    .collect(Collectors.toSet());
            existingCampaign.setKeywords(keywords.stream()
                    .map(keywordMapper::toEntity)
                    .collect(Collectors.toSet()));
        }

        Campaign savedCampaign = campaignRepository.save(existingCampaign);
        log.info("Campaign updated: {}", savedCampaign);

        return campaignMapper.toResponse(savedCampaign);
    }

    @Transactional
    public void deleteCampaign(UUID campaignId) {
        log.info("Deleting campaign with ID: {}", campaignId);
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> {
                    log.error("Campaign not found with ID: {}", campaignId);
                    return new IllegalArgumentException("Campaign not found");
                });

        accountService.deposit(campaign.getSeller().getId(), campaign.getCampaignFund());
        log.info("Deposited {} back to seller {}'s account", campaign.getCampaignFund(), campaign.getSeller().getId());

        campaignRepository.delete(campaign);
        log.info("Campaign deleted: {}", campaignId);
    }

    @Transactional(readOnly = true)
    public CampaignResponse getCampaignById(UUID campaignId) {
        log.info("Fetching campaign with ID: {}", campaignId);
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> {
                    log.error("Campaign not found with ID: {}", campaignId);
                    return new IllegalArgumentException("Campaign not found");
                });
        log.info("Fetched campaign: {}", campaign);
        return campaignMapper.toResponse(campaign);
    }

    @Transactional(readOnly = true)
    public Page<CampaignResponse> getAllCampaigns(Pageable pageable) {
        log.info("Fetching all campaigns with pageable: {}", pageable);
        Page<CampaignResponse> response = campaignRepository.findAll(pageable)
                .map(campaignMapper::toResponse);
        log.info("Fetched all campaigns");
        return response;
    }

    @Transactional(readOnly = true)
    public Page<CampaignResponse> getCampaignsBySellerId(UUID sellerId, Pageable pageable) {
        log.info("Fetching campaigns for seller ID: {} with pageable: {}", sellerId, pageable);
        Page<CampaignResponse> response = campaignRepository.findBySellerId(sellerId, pageable)
                .map(campaignMapper::toResponse);
        log.info("Fetched campaigns for seller ID: {}", sellerId);
        return response;
    }
}
