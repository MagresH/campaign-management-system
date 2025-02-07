package com.example.campaignmanagementsystem.keyword;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface KeywordRepository extends JpaRepository<Keyword, UUID> {
    List<Keyword> findByTextContainingIgnoreCase(String query);
    Keyword findByText(String value);
}