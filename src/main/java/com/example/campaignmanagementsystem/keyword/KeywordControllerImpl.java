package com.example.campaignmanagementsystem.keyword;

import com.example.campaignmanagementsystem.api.V1.KeywordController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class KeywordControllerImpl implements KeywordController {
    private final KeywordService keywordService;

    public ResponseEntity<List<String>> getKeywordsByQuery(String query) {
        List<String> keywords = keywordService.getKeywordsByQuery(query);
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }

    public ResponseEntity<KeywordDTO> findOrCreateByValue(String value) {
        KeywordDTO keyword = keywordService.findOrCreateByValue(value);
        return new ResponseEntity<>(keyword, HttpStatus.OK);
    }

    public ResponseEntity<Set<KeywordDTO>> findOrCreateByValues(List<String> values) {
        Set<KeywordDTO> keywords = keywordService.findOrCreateByValues(values);
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }
}
