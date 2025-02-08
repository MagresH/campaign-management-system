package com.example.campaignmanagementsystem.campaign;

import com.example.campaignmanagementsystem.keyword.Keyword;
import com.example.campaignmanagementsystem.location.Location;
import com.example.campaignmanagementsystem.product.Product;
import com.example.campaignmanagementsystem.seller.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "campaign")
@Getter
@Setter
@AllArgsConstructor
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

    public static CampaignBuilder builder() {
        return new CampaignBuilder();
    }

    public static class CampaignBuilder {
        private UUID id;
        private @NotBlank(message = "Campaign name is mandatory") String name;
        private @NotNull(message = "Bid amount is mandatory")
        @DecimalMin(value = "0.01", message = "Bid amount must be at least 0.01") BigDecimal bidAmount;
        private @NotNull(message = "Campaign fund is mandatory")
        @DecimalMin(value = "0.01", message = "Campaign fund must be at least 0.01") BigDecimal campaignFund;
        private @NotNull(message = "Status is mandatory") CampaignStatus status;
        private @NotNull(message = "Location is mandatory") Location location;
        private @NotNull(message = "Radius is mandatory")
        @Min(value = 1, message = "Radius must be at least 1 km") Integer radius;
        private @NotEmpty(message = "At least one keyword is required") Set<Keyword> keywords;
        private @NotNull(message = "Product is mandatory") Product product;
        private @NotNull(message = "Seller is mandatory") Seller seller;

        CampaignBuilder() {
        }

        public CampaignBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public CampaignBuilder name(@NotBlank(message = "Campaign name is mandatory") String name) {
            this.name = name;
            return this;
        }

        public CampaignBuilder bidAmount(@NotNull(message = "Bid amount is mandatory") @DecimalMin(value = "0.01", message = "Bid amount must be at least 0.01") BigDecimal bidAmount) {
            this.bidAmount = bidAmount;
            return this;
        }

        public CampaignBuilder campaignFund(@NotNull(message = "Campaign fund is mandatory") @DecimalMin(value = "0.01", message = "Campaign fund must be at least 0.01") BigDecimal campaignFund) {
            this.campaignFund = campaignFund;
            return this;
        }

        public CampaignBuilder status(@NotNull(message = "Status is mandatory") CampaignStatus status) {
            this.status = status;
            return this;
        }

        public CampaignBuilder location(@NotNull(message = "Location is mandatory") Location location) {
            this.location = location;
            return this;
        }

        public CampaignBuilder radius(@NotNull(message = "Radius is mandatory") @Min(value = 1, message = "Radius must be at least 1 km") Integer radius) {
            this.radius = radius;
            return this;
        }

        public CampaignBuilder keywords(@NotEmpty(message = "At least one keyword is required") Set<Keyword> keywords) {
            this.keywords = keywords;
            return this;
        }

        public CampaignBuilder product(@NotNull(message = "Product is mandatory") Product product) {
            this.product = product;
            return this;
        }

        public CampaignBuilder seller(@NotNull(message = "Seller is mandatory") Seller seller) {
            this.seller = seller;
            return this;
        }

        public Campaign build() {
            return new Campaign(this.id, this.name, this.bidAmount, this.campaignFund, this.status, this.location, this.radius, this.keywords, this.product, this.seller);
        }

        public String toString() {
            return "Campaign.CampaignBuilder(id=" + this.id + ", name=" + this.name + ", bidAmount=" + this.bidAmount + ", campaignFund=" + this.campaignFund + ", status=" + this.status + ", location=" + this.location + ", radius=" + this.radius + ", keywords=" + this.keywords + ", product=" + this.product + ", seller=" + this.seller + ")";
        }
    }
    public String getTown() {
        return location.getTown();
    }
}