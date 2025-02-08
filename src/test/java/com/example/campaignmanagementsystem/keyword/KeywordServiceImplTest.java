package com.example.campaignmanagementsystem.keyword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KeywordServiceImplTest {

    @InjectMocks
    private KeywordServiceImpl keywordService;

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private KeywordMapper keywordMapper;

    @Test
    public void testFindOrCreateByValue_ExistingKeyword() {
        String value = "test";
        Keyword keyword = Keyword.builder()
                .id(UUID.randomUUID())
                .text(value).build();

        when(keywordRepository.findByValueIgnoreCase(value)).thenReturn(Optional.of(keyword));
        when(keywordMapper.toDto(keyword)).thenReturn(new KeywordDTO(keyword.getId(), keyword.getText()));

        KeywordDTO result = keywordService.findOrCreateByValue(value);

        assertNotNull(result);
        assertEquals(value, result.text());

        verify(keywordRepository, never()).save(any(Keyword.class));
    }

    @Test
    public void testFindOrCreateByValue_NewKeyword() {
        String value = "newKeyword";
        when(keywordRepository.findByValueIgnoreCase(value)).thenReturn(Optional.empty());

        Keyword keyword = new Keyword();
        keyword.setText(value);

        Keyword savedKeyword = Keyword.builder()
                .id(UUID.randomUUID())
                .text(value).build();

        when(keywordRepository.save(any(Keyword.class))).thenReturn(savedKeyword);
        when(keywordMapper.toDto(savedKeyword)).thenReturn(new KeywordDTO(savedKeyword.getId(), savedKeyword.getText()));

        KeywordDTO result = keywordService.findOrCreateByValue(value);

        assertNotNull(result);
        assertEquals(value, result.text());

        verify(keywordRepository).save(any(Keyword.class));
    }

}
