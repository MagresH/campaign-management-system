package com.example.campaignmanagementsystem.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordMapper keywordMapper;

    @Transactional
    public KeywordDTO findOrCreateByValue(String value) {
        Optional<Keyword> optionalKeyword = keywordRepository.findByValueIgnoreCase(value);
        if (optionalKeyword.isPresent()) {
            return keywordMapper.toDto(optionalKeyword.get());
        } else {
            Keyword keyword = Keyword.builder().text(value).build();
            Keyword savedKeyword = keywordRepository.save(keyword);
            return keywordMapper.toDto(savedKeyword);
        }
    }

    @Transactional
    public Set<KeywordDTO> findOrCreateByValues(List<String> values) {
        return values.stream()
                .map(this::findOrCreateByValue)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public List<String> getKeywordsByQuery(String query) {
        List<Keyword> keywords = keywordRepository.findByValueContainingIgnoreCase(query);
        return keywords.stream()
                .map(Keyword::getText)
                .collect(Collectors.toList());
    }
}
