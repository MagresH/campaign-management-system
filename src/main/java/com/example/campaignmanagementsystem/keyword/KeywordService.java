package com.example.campaignmanagementsystem.keyword;

import java.util.List;
import java.util.Set;

public interface KeywordService {

    List<String> getKeywordsByQuery(String query);

    KeywordDTO findOrCreateByValue(String value);

    Set<KeywordDTO> findOrCreateByValues(List<String> values);
}
