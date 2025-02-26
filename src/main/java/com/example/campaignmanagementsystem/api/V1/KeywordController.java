package com.example.campaignmanagementsystem.api.V1;

import com.example.campaignmanagementsystem.keyword.KeywordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/keywords")
@Tag(name = "Keyword", description = "Operations related to keywords")
public interface KeywordController {

    @Operation(summary = "Get keywords based on query")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keywords retrieved")
    })
    @GetMapping
    ResponseEntity<List<String>> getKeywordsByQuery(@RequestParam String query);

    @Operation(summary = "Find or create a keyword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keyword retrieved or created")
    })
    @PostMapping
    ResponseEntity<KeywordDTO> findOrCreateByValue(@RequestParam String value);

    @Operation(summary = "Find or create multiple keywords")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keywords retrieved or created")
    })
    @PostMapping("/batch")
    ResponseEntity<Set<KeywordDTO>> findOrCreateByValues(@RequestBody List<String> values);
}
