package com.example.campaignmanagementsystem.keyword;

import com.example.campaignmanagementsystem.api.V1.KeywordController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KeywordControllerImpl implements KeywordController {
    private final KeywordService keywordService;

    public ResponseEntity<List<String>> getKeywordsByQuery(String query) {
        log.info("Fetching keywords with query: {}", query);
        List<String> keywords = keywordService.getKeywordsByQuery(query);
        log.info("Fetched keywords: {}", keywords);
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }

    public ResponseEntity<KeywordDTO> findOrCreateByValue(String value) {
        log.info("Finding or creating keyword with value: {}", value);
        KeywordDTO keyword = keywordService.findOrCreateByValue(value);
        log.info("Found or created keyword: {}", keyword);
        return new ResponseEntity<>(keyword, HttpStatus.OK);
    }

    public ResponseEntity<Set<KeywordDTO>> findOrCreateByValues(List<String> values) {
        log.info("Finding or creating keywords with values: {}", values);
        Set<KeywordDTO> keywords = keywordService.findOrCreateByValues(values);
        log.info("Found or created keywords: {}", keywords);
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }
}
