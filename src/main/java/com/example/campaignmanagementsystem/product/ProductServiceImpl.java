package com.example.campaignmanagementsystem.product;

import com.example.campaignmanagementsystem.seller.Seller;
import com.example.campaignmanagementsystem.seller.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(UUID productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        if (productDTO.name() != null && !productDTO.name().isBlank()) {
            existingProduct.setName(productDTO.name());
        }

        if (productDTO.description() != null) {
            existingProduct.setDescription(productDTO.description());
        }
        if (productDTO.price() != null) {
            existingProduct.setPrice(productDTO.price());
        }

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(UUID productId) {
        return productRepository.findById(productId)
                .map(productMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsBySellerId(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + sellerId));

        List<Product> products = productRepository.findBySellerId(sellerId);

        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

}
