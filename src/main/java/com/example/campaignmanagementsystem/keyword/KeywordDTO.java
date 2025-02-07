package com.example.campaignmanagementsystem.keyword;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Keyword}
 */
public record KeywordDTO(UUID id, String text) implements Serializable {
}