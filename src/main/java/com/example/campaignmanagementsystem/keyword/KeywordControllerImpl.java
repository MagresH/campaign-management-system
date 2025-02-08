package com.example.campaignmanagementsystem.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class KeywordControllerImpl implements KeywordController {
    private final KeywordService keywordService;

    public List<String> getKeywordsByQuery(String query) {
        return keywordService.getKeywordsByQuery(query);
    }

    public KeywordDTO findOrCreateByValue(String value) {
        return keywordService.findOrCreateByValue(value);
    }

    public Set<KeywordDTO> findOrCreateByValues(List<String> values) {
        return keywordService.findOrCreateByValues(values);
    }
}
