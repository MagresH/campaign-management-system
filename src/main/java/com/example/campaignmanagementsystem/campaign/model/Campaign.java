package com.example.campaignmanagementsystem.campaign.model;

import com.example.campaignmanagementsystem.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "campaign")
@Getter
@Setter
@NoArgsConstructor
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

    @NotBlank(message = "Town is mandatory")
    @Column(name = "town", nullable = false)
    private String town;

    @NotNull(message = "Radius is mandatory")
    @Min(value = 1, message = "Radius must be at least 1 km")
    @Column(name = "radius", nullable = false)
    private Integer radius;

    @NotEmpty(message = "At least one keyword is required")
    @ElementCollection
    @CollectionTable(name = "campaign_keywords", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "keyword", nullable = false)
    private List<@NotBlank(message = "Keyword cannot be blank") String> keywords;

    @NotNull(message = "Product is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder
    public Campaign(String name, BigDecimal bidAmount, BigDecimal campaignFund,
                    CampaignStatus status, Integer radius, String town, List<String> keywords,
                    Product product) {
        this.name = name;
        this.bidAmount = bidAmount;
        this.campaignFund = campaignFund;
        this.status = status;
        this.radius = radius;
        this.town = town;
        this.keywords = keywords;
        this.product = product;
    }
}