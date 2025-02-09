package com.example.campaignmanagementsystem.product;

import com.example.campaignmanagementsystem.exception.SellerNotFoundException;
import com.example.campaignmanagementsystem.seller.Seller;
import com.example.campaignmanagementsystem.seller.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        log.info("Creating product with details: {}", productDTO);
        Product product = productMapper.toEntity(productDTO);

        Product savedProduct = productRepository.save(product);
        log.info("Product created: {}", savedProduct);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(UUID productId, ProductDTO productDTO) {
        log.info("Updating product with ID: {} and details: {}", productId, productDTO);
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new EntityNotFoundException("Product not found with ID: " + productId);
                });

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
        log.info("Product updated: {}", updatedProduct);

        return productMapper.toDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        log.info("Deleting product with ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new EntityNotFoundException("Product not found with ID: " + productId);
                });

        productRepository.delete(product);
        log.info("Product deleted: {}", productId);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(UUID productId) {
        log.info("Fetching product with ID: {}", productId);
        return productRepository.findById(productId)
                .map(productMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new EntityNotFoundException("Product not found with ID: " + productId);
                });
    }

    @Transactional(readOnly = true)
    public Product getProductEntityById(UUID productId) {
        log.info("Fetching product entity with ID: {}", productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new EntityNotFoundException("Product not found with ID: " + productId);
                });
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsBySellerId(UUID sellerId) {
        log.info("Fetching products for seller ID: {}", sellerId);
        sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller not found with ID: {}", sellerId);
                    return new SellerNotFoundException("Seller not found with ID: " + sellerId);
                });

        List<Product> products = productRepository.findBySellerId(sellerId);
        log.info("Fetched products: {}", products);

        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProductForSeller(UUID sellerId, ProductDTO productDTO) {
        log.info("Creating product for seller ID: {} with details: {}", sellerId, productDTO);
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.error("Seller not found with ID: {}", sellerId);
                    return new SellerNotFoundException("Seller not found with ID: " + sellerId);
                });

        Product product = productMapper.toEntity(productDTO);
        product.setSeller(seller);

        Product savedProduct = productRepository.save(product);
        log.info("Product created: {}", savedProduct);
        return productMapper.toDto(savedProduct);
    }

}
