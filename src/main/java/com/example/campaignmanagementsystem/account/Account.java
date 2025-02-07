package com.example.campaignmanagementsystem.account;


import com.example.campaignmanagementsystem.seller.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "Balance is mandatory")
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Balance must have up to 15 digits and 2 decimals")
    @Column(name = "balance", nullable = false, precision = 17, scale = 2)
    private BigDecimal balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public static class AccountBuilder {
        private UUID id;
        private @NotNull(message = "Balance is mandatory")
        @DecimalMin(value = "0.00", message = "Balance cannot be negative")
        @Digits(integer = 15, fraction = 2, message = "Balance must have up to 15 digits and 2 decimals") BigDecimal balance;
        private Seller seller;

        AccountBuilder() {
        }

        public AccountBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AccountBuilder balance(@NotNull(message = "Balance is mandatory") @DecimalMin(value = "0.00", message = "Balance cannot be negative") @Digits(integer = 15, fraction = 2, message = "Balance must have up to 15 digits and 2 decimals") BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public AccountBuilder seller(Seller seller) {
            this.seller = seller;
            return this;
        }

        public Account build() {
            return new Account(this.id, this.balance, this.seller);
        }

        public String toString() {
            return "Account.AccountBuilder(id=" + this.id + ", balance=" + this.balance + ", seller=" + this.seller + ")";
        }
    }
}
