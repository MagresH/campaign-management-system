package com.example.campaignmanagementsystem.product;

import com.example.campaignmanagementsystem.campaign.Campaign;
import com.example.campaignmanagementsystem.seller.Seller;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    private String description;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Campaign> campaigns;

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private UUID id;
        private String name;
        private String description;
        private BigDecimal price;
        private Seller seller;
        private List<Campaign> campaigns;

        ProductBuilder() {
        }

        public ProductBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder seller(Seller seller) {
            this.seller = seller;
            return this;
        }

        public ProductBuilder campaigns(List<Campaign> campaigns) {
            this.campaigns = campaigns;
            return this;
        }

        public Product build() {
            return new Product(this.id, this.name, this.description, this.price, this.seller, this.campaigns);
        }

        public String toString() {
            return "Product.ProductBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", price=" + this.price + ", seller=" + this.seller + ", campaigns=" + this.campaigns + ")";
        }
    }
}