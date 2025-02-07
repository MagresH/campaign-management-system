package com.example.campaignmanagementsystem.seller.model;

import com.example.campaignmanagementsystem.account.model.Account;
import com.example.campaignmanagementsystem.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "campaign")
@Getter
@Setter
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Seller name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL, optional = false)
    private Account account;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    @Builder
    public Seller(String name, Account account) {
        this.name = name;
        this.account = account;
    }
}
