package com.example.campaignmanagementsystem.account;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link Account}
 */
public record AccountDTO(UUID id,
                         @NotNull(message = "Balance is mandatory") @Digits(message = "Balance must have up to 15 digits and 2 decimals", integer = 15, fraction = 2)
                         BigDecimal balance
) implements Serializable {
}