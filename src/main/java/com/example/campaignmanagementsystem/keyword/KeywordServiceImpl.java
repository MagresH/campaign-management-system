package com.example.campaignmanagementsystem.keyword;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    private final KeywordMapper keywordMapper;

    @Transactional
    public KeywordDTO findOrCreateByValue(String value) {
        log.info("Finding or creating keyword with value: {}", value);
        Optional<Keyword> optionalKeyword = keywordRepository.findByTextIgnoreCase(value);
        if (optionalKeyword.isPresent()) {
            log.info("Keyword found: {}", optionalKeyword.get());
            return keywordMapper.toDto(optionalKeyword.get());
        } else {
            Keyword keyword = Keyword.builder().text(value).build();
            Keyword savedKeyword = keywordRepository.save(keyword);
            log.info("Keyword created: {}", savedKeyword);
            return keywordMapper.toDto(savedKeyword);
        }
    }

    @Transactional
    public Set<KeywordDTO> findOrCreateByValues(List<String> values) {
        log.info("Finding or creating keywords with values: {}", values);
        Set<KeywordDTO> keywords = values.stream()
                .map(this::findOrCreateByValue)
                .collect(Collectors.toSet());
        log.info("Keywords found or created: {}", keywords);
        return keywords;
    }

    @Transactional(readOnly = true)
    public List<String> getKeywordsByQuery(String query) {
        log.info("Fetching keywords with query: {}", query);
        List<Keyword> keywords = keywordRepository.findByTextContainingIgnoreCase(query);
        List<String> keywordTexts = keywords.stream()
                .map(Keyword::getText)
                .collect(Collectors.toList());
        log.info("Fetched keywords: {}", keywordTexts);
        return keywordTexts;
    }
}
