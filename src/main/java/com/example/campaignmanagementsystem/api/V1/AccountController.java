package com.example.campaignmanagementsystem.api.V1;

import com.example.campaignmanagementsystem.account.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
@Tag(name = "Account", description = "Operations related to the seller's account")
public interface AccountController {

    @Operation(summary = "Get seller's account balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account balance retrieved"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    @GetMapping("/{sellerId}/balance")
    BigDecimal getBalanceBySellerId(@PathVariable UUID sellerId);

    @Operation(summary = "Deposit funds into seller's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funds deposited"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    @PostMapping("/{sellerId}/deposit")
    void deposit(@PathVariable UUID sellerId, @RequestParam BigDecimal amount);

    @Operation(summary = "Withdraw funds from seller's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funds withdrawn"),
            @ApiResponse(responseCode = "404", description = "Seller not found"),
            @ApiResponse(responseCode = "400", description = "Insufficient funds")
    })
    @PostMapping("/{sellerId}/withdraw")
    void withdraw(@PathVariable UUID sellerId, @RequestParam BigDecimal amount);

    @Operation(summary = "Check if seller has sufficient funds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funds checked"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    @GetMapping("/{sellerId}/has-sufficient-funds")
    boolean hasSufficientFunds(@PathVariable UUID sellerId, @RequestParam BigDecimal amount);

    @Operation(summary = "Create an account for the seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created")
    })
    @PostMapping("/{sellerId}")
    Account createAccount(@PathVariable UUID sellerId, @RequestParam BigDecimal initialBalance);

    @Operation(summary = "Delete seller's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Account deleted"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    @DeleteMapping("/{sellerId}")
    void deleteAccount(@PathVariable UUID sellerId);
}
