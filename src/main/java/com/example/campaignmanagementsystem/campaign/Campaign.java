package com.example.campaignmanagementsystem.campaign;

import com.example.campaignmanagementsystem.keyword.Keyword;
import com.example.campaignmanagementsystem.location.Location;
import com.example.campaignmanagementsystem.product.Product;
import com.example.campaignmanagementsystem.seller.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "campaign")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Campaign name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Bid amount is mandatory")
    @DecimalMin(value = "0.01", message = "Bid amount must be at least 0.01")
    @Column(name = "bid_amount", nullable = false)
    private BigDecimal bidAmount;

    @NotNull(message = "Campaign fund is mandatory")
    @DecimalMin(value = "0.01", message = "Campaign fund must be at least 0.01")
    @Column(name = "campaign_fund", nullable = false)
    private BigDecimal campaignFund;

    @NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CampaignStatus status;

    @NotNull(message = "Location is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @NotNull(message = "Radius is mandatory")
    @Min(value = 1, message = "Radius must be at least 1 km")
    @Column(name = "radius", nullable = false)
    private Integer radius;

    @NotEmpty(message = "At least one keyword is required")
    @ManyToMany
    private Set<Keyword> keywords;

    @NotNull(message = "Product is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Seller is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
}