package com.example.campaignmanagementsystem.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link Product}
 */
public record ProductDTO(UUID id, String name, String description, BigDecimal price) implements Serializable {
}