package com.example.campaignmanagementsystem.account.model;


import com.example.campaignmanagementsystem.seller.model.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "Ballance is mandatory")
    @DecimalMin(value = "0.00", message = "Ballance cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Ballance must have up to 15 digits and 2 decimals")
    @Column(name = "balance", nullable = false, precision = 17, scale = 2)
    private BigDecimal balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Builder
    public Account(BigDecimal balance, Seller seller) {
        this.balance = balance;
        this.seller = seller;
    }
}
