package com.example.campaignmanagementsystem.product;

import com.example.campaignmanagementsystem.api.V1.ProductController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    public ResponseEntity<ProductDTO> createProductForSeller(UUID sellerId, ProductDTO productDTO) {
        log.info("Creating product for seller ID: {} with details: {}", sellerId, productDTO);
        ProductDTO createdProduct = productService.createProductForSeller(sellerId, productDTO);
        log.info("Product created: {}", createdProduct);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    public ResponseEntity<ProductDTO> updateProduct(UUID productId, ProductDTO productDTO) {
        log.info("Updating product with ID: {} and details: {}", productId, productDTO);
        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
        log.info("Product updated: {}", updatedProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProduct(UUID productId) {
        log.info("Deleting product with ID: {}", productId);
        productService.deleteProduct(productId);
        log.info("Product deleted: {}", productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ProductDTO> getProductById(UUID productId) {
        log.info("Fetching product with ID: {}", productId);
        ProductDTO product = productService.getProductById(productId);
        log.info("Fetched product: {}", product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<List<ProductDTO>> getProductsBySellerId(UUID sellerId) {
        log.info("Fetching products for seller ID: {}", sellerId);
        List<ProductDTO> products = productService.getProductsBySellerId(sellerId);
        log.info("Fetched products for seller ID: {}", sellerId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
