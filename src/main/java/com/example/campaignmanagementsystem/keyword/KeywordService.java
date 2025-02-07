package com.example.campaignmanagementsystem.keyword;

import java.util.List;

public interface KeywordService {

    List<String> getKeywordsByQuery(String query);

    KeywordDTO findOrCreateByValue(String value);

    List<Keyword> getAllKeywords();
}
