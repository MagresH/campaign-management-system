package com.example.campaignmanagementsystem.campaign;

import com.example.campaignmanagementsystem.account.AccountService;
import com.example.campaignmanagementsystem.keyword.KeywordDTO;
import com.example.campaignmanagementsystem.keyword.KeywordService;
import com.example.campaignmanagementsystem.location.Location;
import com.example.campaignmanagementsystem.location.LocationDTO;
import com.example.campaignmanagementsystem.location.LocationService;
import com.example.campaignmanagementsystem.product.Product;
import com.example.campaignmanagementsystem.product.ProductDTO;
import com.example.campaignmanagementsystem.product.ProductService;
import com.example.campaignmanagementsystem.seller.Seller;
import com.example.campaignmanagementsystem.seller.SellerDTO;
import com.example.campaignmanagementsystem.seller.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private KeywordService keywordService;

    @Mock
    private AccountService accountService;

    @Mock
    private ProductService productService;

    @Mock
    private SellerService sellerService;

    @Mock
    private LocationService locationService;

    @Mock
    private CampaignMapper campaignMapper;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testGetAllCampaigns_WithPagination() {

        Location location1 = Location.builder()
                .id(UUID.randomUUID())
                .town("Warsaw")
                .build();

        Location location2 = Location.builder()
                .id(UUID.randomUUID())
                .town("Krakow")
                .build();

        Seller seller = Seller.builder()
                .id(UUID.randomUUID())
                .name("Seller")
                .build();

        Campaign campaign1 = Campaign.builder()
                .id(UUID.randomUUID())
                .name("Campaign 1")
                .bidAmount(BigDecimal.valueOf(1.0))
                .campaignFund(BigDecimal.valueOf(100.0))
                .status(CampaignStatus.ON)
                .radius(10)
                .location(location1)
                .keywords(new HashSet<>())
                .product(new Product())
                .seller(seller)
                .build();

        Campaign campaign2 = Campaign.builder()
                .id(UUID.randomUUID())
                .name("Campaign 2")
                .bidAmount(BigDecimal.valueOf(2.0))
                .campaignFund(BigDecimal.valueOf(200.0))
                .status(CampaignStatus.OFF)
                .radius(20)
                .location(location2)
                .keywords(new HashSet<>())
                .product(new Product())
                .seller(seller)
                .build();

        List<Campaign> campaigns = Arrays.asList(campaign1, campaign2);
        Page<Campaign> campaignPage = new PageImpl<>(campaigns);

        when(campaignMapper.toResponse(any(Campaign.class))).thenAnswer(invocation -> {
            Campaign campaign = invocation.getArgument(0);
            return new CampaignResponse(
                    campaign.getId(),
                    campaign.getName(),
                    campaign.getBidAmount(),
                    campaign.getCampaignFund(),
                    campaign.getStatus(),
                    campaign.getRadius(),
                    campaign.getLocation().getTown(),
                    new ArrayList<>(),
                    campaign.getProduct().getId(),
                    campaign.getSeller().getId()
            );
        });
        when(campaignRepository.findAll(any(Pageable.class))).thenReturn(campaignPage);

        Pageable pageable = PageRequest.of(0, 10);
        Page<CampaignResponse> result = campaignService.getAllCampaigns(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertNotNull(result.getContent().get(0));
        assertNotNull(result.getContent().get(1));
        assertEquals("Campaign 1", result.getContent().get(0).name());
        assertEquals("Campaign 2", result.getContent().get(1).name());

        verify(campaignRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testCreateCampaign_Success() {
        UUID sellerId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        CreateCampaignRequest request = new CreateCampaignRequest(
                "Test Campaign",
                BigDecimal.valueOf(1.0),
                BigDecimal.valueOf(100.0),
                CampaignStatus.ON,
                10,
                "Warsaw",
                Collections.singletonList("keyword1"),
                productId,
                sellerId
        );

        SellerDTO seller = new SellerDTO(
                sellerId,
                "Seller"
        );

        when(sellerService.getSellerById(sellerId)).thenReturn(seller);

        ProductDTO product = new ProductDTO(
                productId,
                "Product"
        );
        when(productService.getProductById(productId)).thenReturn(product);

        when(accountService.hasSufficientFunds(sellerId, request.campaignFund())).thenReturn(true);
        doNothing().when(accountService).withdraw(sellerId, request.campaignFund());

        KeywordDTO keyword = new KeywordDTO(UUID.randomUUID(), "keyword1");
        when(keywordService.findOrCreateByValue("keyword1")).thenReturn(keyword);

        LocationDTO location = new LocationDTO(UUID.randomUUID(), "Warsaw");
        when(locationService.getLocationByTown("Warsaw")).thenReturn(location);

        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocation -> {
            Campaign campaign = invocation.getArgument(0);
            campaign.setId(UUID.randomUUID());
            return campaign;
        });

        when(campaignMapper.toResponse(any(Campaign.class))).thenAnswer(invocation -> {
            Campaign campaign = invocation.getArgument(0);
            return new CampaignResponse(
                    campaign.getId(),
                    campaign.getName(),
                    campaign.getBidAmount(),
                    campaign.getCampaignFund(),
                    campaign.getStatus(),
                    campaign.getRadius(),
                    campaign.getLocation().getTown(),
                    new ArrayList<>(),
                    campaign.getProduct().getId(),
                    campaign.getSeller().getId()
            );
        });

        CampaignResponse response = campaignService.createCampaign(request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        assertEquals(request.bidAmount(), response.bidAmount());
        assertEquals(request.campaignFund(), response.campaignFund());
        assertEquals(request.status(), response.status());
        assertEquals(request.radius(), response.radius());
        assertEquals(request.town(), response.town());
        assertEquals(request.keywords(), response.keywords());
        assertEquals(request.productId(), response.productId());
        assertEquals(request.sellerId(), response.sellerId());

        verify(accountService, times(1)).hasSufficientFunds(sellerId, request.campaignFund());
        verify(accountService, times(1)).withdraw(sellerId, request.campaignFund());
        verify(campaignRepository, times(1)).save(any(Campaign.class));
    }
}
