package com.example.campaignmanagementsystem.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    public ProductDTO createProduct(ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    public ProductDTO updateProduct(UUID productId, ProductDTO productDTO) {
        return productService.updateProduct(productId, productDTO);
    }

    public void deleteProduct(UUID productId) {
        productService.deleteProduct(productId);
    }

    public ProductDTO getProductById(UUID productId) {
        return productService.getProductById(productId);
    }

    public List<ProductDTO> getProductsBySellerId(UUID sellerId) {
        return productService.getProductsBySellerId(sellerId);
    }
}
