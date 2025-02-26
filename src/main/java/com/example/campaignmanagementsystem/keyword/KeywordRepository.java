package com.example.campaignmanagementsystem.keyword;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KeywordRepository extends JpaRepository<Keyword, UUID> {

    Optional<Keyword> findByTextIgnoreCase(String value);

    List<Keyword> findByTextContainingIgnoreCase(String query);
}