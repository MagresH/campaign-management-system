package com.example.campaignmanagementsystem.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(UUID productId, ProductDTO productDTO);

    void deleteProduct(UUID productId);

    Optional<ProductDTO> getProductById(UUID productId);

    List<ProductDTO> getProductsBySellerId(UUID sellerId);
}
