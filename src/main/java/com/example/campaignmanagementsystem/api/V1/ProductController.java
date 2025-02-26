package com.example.campaignmanagementsystem.api.V1;

import com.example.campaignmanagementsystem.product.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
@Tag(name = "Product", description = "Operations related to products")
public interface ProductController {

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created")
    })
    @PostMapping("/seller/{sellerId}")
    ResponseEntity<ProductDTO> createProductForSeller(@PathVariable("sellerId") UUID sellerId, @RequestBody ProductDTO productDTO);

    @Operation(summary = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{productId}")
    ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID productId, @RequestBody ProductDTO productDTO);

    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{productId}")
    ResponseEntity<Void> deleteProduct(@PathVariable UUID productId);

    @Operation(summary = "Get product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{productId}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable UUID productId);

    @Operation(summary = "Get products by seller ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of seller's products retrieved")
    })
    @GetMapping("/seller/{sellerId}")
    ResponseEntity<List<ProductDTO>> getProductsBySellerId(@PathVariable UUID sellerId);
}
