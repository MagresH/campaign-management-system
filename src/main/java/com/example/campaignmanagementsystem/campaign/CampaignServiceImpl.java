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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    private final KeywordService keywordService;
    private final AccountService accountService;
    private final ProductService productService;
    private final SellerService sellerService;
    private final LocationService locationService;
    private final KeywordMapper keywordMapper;
    private final LocationMapper locationMapper;
    private final ProductMapper productMapper;
    private final SellerMapper sellerMapper;

    @Transactional
    public CampaignResponse createCampaign(CreateCampaignRequest request) {
        SellerDTO seller = sellerService.getSellerById(request.sellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));

        ProductDTO product = productService.getProductById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        LocationDTO location = locationService.getLocationByTown(request.town())
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        BigDecimal campaignFund = request.campaignFund();
        UUID sellerId = seller.id();

        if (!accountService.hasSufficientFunds(sellerId, campaignFund)) {
            throw new InsufficientFundsException("Insufficient funds");
        } else accountService.withdraw(sellerId, campaignFund);

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

        return campaignMapper.toResponse(savedCampaign);

    }

    @Transactional
    public CampaignResponse updateCampaign(UpdateCampaignRequest request) {
        Campaign existingCampaign = campaignRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found"));

        SellerDTO seller = null;
        if (request.getSellerId() != null) {
            seller = sellerService.getSellerById(request.getSellerId())
                    .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
            existingCampaign.setSeller(sellerMapper.toEntity(seller));
        }

        ProductDTO product = null;
        if (request.getProductId() != null) {
            product = productService.getProductById(request.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            existingCampaign.setProduct(productMapper.toEntity(product));
        }

        if (request.getTown() != null && !request.getTown().isBlank()) {
            LocationDTO location = locationService.getLocationByTown(request.getTown())
                    .orElseThrow(() -> new EntityNotFoundException("City not found"));
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
                    throw new InsufficientFundsException("Insufficient funds");
                }
                accountService.withdraw(sellerId, difference);
            } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
                accountService.deposit(sellerId, difference.abs());
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

        return campaignMapper.toResponse(savedCampaign);
    }

    @Transactional
    public void deleteCampaign(UUID campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));

        accountService.deposit(campaign.getSeller().getId(), campaign.getCampaignFund());

        campaignRepository.delete(campaign);
    }

    @Transactional(readOnly = true)
    public CampaignResponse getCampaignById(UUID campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));
        return campaignMapper.toResponse(campaign);
    }

    @Transactional(readOnly = true)
    public Page<CampaignResponse> getAllCampaigns(Pageable pageable) {
        return campaignRepository.findAll(pageable)
                .map(campaignMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<CampaignResponse> getCampaignsBySellerId(UUID sellerId, Pageable pageable) {
        return campaignRepository.findBySellerId(sellerId, pageable)
                .map(campaignMapper::toResponse);
    }
}
