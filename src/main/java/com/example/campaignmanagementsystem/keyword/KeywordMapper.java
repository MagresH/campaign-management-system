package com.example.campaignmanagementsystem.keyword;

import org.springframework.stereotype.Service;

@Service
public class KeywordMapper {

    public Keyword toEntity(KeywordDTO keywordDto) {
        if (keywordDto == null) {
            return null;
        }
        Keyword keyword = new Keyword();
        keyword.setId(keywordDto.id());
        keyword.setText(keywordDto.text());
        return keyword;
    }

    public KeywordDTO toDto(Keyword keyword) {
        if (keyword == null) {
            return null;
        }
        return new KeywordDTO(
                keyword.getId(),
                keyword.getText()
        );
    }

}