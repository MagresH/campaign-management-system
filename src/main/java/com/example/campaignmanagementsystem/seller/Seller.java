package com.example.campaignmanagementsystem.seller;

import com.example.campaignmanagementsystem.account.Account;
import com.example.campaignmanagementsystem.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "seller")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Seller name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    public static SellerBuilder builder() {
        return new SellerBuilder();
    }

    public static class SellerBuilder {
        private UUID id;
        private @NotBlank(message = "Seller name is mandatory") String name;
        private Account account;
        private List<Product> products;

        SellerBuilder() {
        }

        public SellerBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public SellerBuilder name(@NotBlank(message = "Seller name is mandatory") String name) {
            this.name = name;
            return this;
        }

        public SellerBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public SellerBuilder products(List<Product> products) {
            this.products = products;
            return this;
        }

        public Seller build() {
            return new Seller(this.id, this.name, this.account, this.products);
        }

        public String toString() {
            return "Seller.SellerBuilder(id=" + this.id + ", name=" + this.name + ", account=" + this.account + ", products=" + this.products + ")";
        }
    }
}
