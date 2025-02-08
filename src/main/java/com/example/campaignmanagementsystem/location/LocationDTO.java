package com.example.campaignmanagementsystem.location;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Location}
 */
public record LocationDTO(UUID id, String town) implements Serializable {
}