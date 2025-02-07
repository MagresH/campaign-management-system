package com.example.campaignmanagementsystem.seller;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Seller}
 */
public record SellerDTO(UUID id, @NotBlank(message = "Seller name is mandatory") String name) implements Serializable {
}