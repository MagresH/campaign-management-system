package com.example.campaignmanagementsystem.product;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Product}
 */
public record ProductDTO(UUID id, String name) implements Serializable {
}