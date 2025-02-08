package com.example.campaignmanagementsystem.product;

import com.example.campaignmanagementsystem.api.V1.ProductController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    public ResponseEntity<ProductDTO> createProductForSeller(UUID sellerId, ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProductForSeller(sellerId, productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    public ResponseEntity<ProductDTO> updateProduct(UUID productId, ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProduct(UUID productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ProductDTO> getProductById(UUID productId) {
        ProductDTO product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<List<ProductDTO>> getProductsBySellerId(UUID sellerId) {
        List<ProductDTO> products = productService.getProductsBySellerId(sellerId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
