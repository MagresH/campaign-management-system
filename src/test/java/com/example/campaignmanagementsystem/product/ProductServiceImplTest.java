package com.example.campaignmanagementsystem.product;

import static org.junit.jupiter.api.Assertions.*;

import com.example.campaignmanagementsystem.seller.Seller;
import com.example.campaignmanagementsystem.seller.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private UUID productId;
    private UUID sellerId;
    private Product product;
    private Seller seller;

    @BeforeEach
    public void setUp() {
        productId = UUID.randomUUID();
        sellerId = UUID.randomUUID();

        seller = new Seller();
        seller.setId(sellerId);
        seller.setName("Test Seller");

        product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setSeller(seller);
    }

    @Test
    public void testCreateProduct_Success() {
        UUID sellerId = UUID.randomUUID();
        Seller seller = new Seller();
        seller.setId(sellerId);

        ProductDTO productDTO = new ProductDTO(
                null,
                "New Product",
                "New Description",
                BigDecimal.valueOf(49.99)
                );

        when(productMapper.toEntity(productDTO)).thenAnswer(invocation -> {
            Product product = new Product();
            product.setName(productDTO.name());
            product.setDescription(productDTO.description());
            product.setPrice(productDTO.price());
            product.setSeller(seller);
            return product;
        });

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId(UUID.randomUUID());
            return savedProduct;
        });

        when(productMapper.toDto(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            return new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice()
            );
        });

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("New Product", result.name());
        assertEquals("New Description", result.description());
        assertEquals(BigDecimal.valueOf(49.99), result.price());
    }

    @Test
    public void testUpdateProduct_Success() {
        UUID newSellerId = UUID.randomUUID();
        Seller newSeller = new Seller();
        newSeller.setId(newSellerId);
        newSeller.setName("New Seller");

        ProductDTO productDTO = new ProductDTO(
                productId,
                "Updated Product",
                "Updated Description",
                BigDecimal.valueOf(79.99)
                );

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(productMapper.toDto(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            return new ProductDTO(
                    savedProduct.getId(),
                    savedProduct.getName(),
                    savedProduct.getDescription(),
                    savedProduct.getPrice()
            );
        });

        ProductDTO result = productService.updateProduct(productId, productDTO);

        assertNotNull(result);
        assertEquals(productId, result.id());
        assertEquals("Updated Product", result.name());
        assertEquals("Updated Description", result.description());
        assertEquals(BigDecimal.valueOf(79.99), result.price());
    }
            @Test
    public void testUpdateProduct_ProductNotFound() {
        UUID nonExistingProductId = UUID.randomUUID();
        ProductDTO productDTO = new ProductDTO(
                null,
                "Updated Product",
                "Updated Description",
                BigDecimal.valueOf(79.99)
        );

        when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.updateProduct(nonExistingProductId, productDTO));

        verify(productRepository).findById(nonExistingProductId);
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    public void testDeleteProduct_Success() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProduct(productId);

        verify(productRepository).findById(productId);
        verify(productRepository).delete(product);
    }

    @Test
    public void testDeleteProduct_NotFound() {
        UUID nonExistingProductId = UUID.randomUUID();
        when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(nonExistingProductId));

        verify(productRepository).findById(nonExistingProductId);
        verify(productRepository, never()).delete(any(Product.class));
    }

    @Test
    public void testGetProductById_Success() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        ));

        ProductDTO result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.id());
        assertEquals("Test Product", result.name());
        assertEquals("Test Description", result.description());
        assertEquals(BigDecimal.valueOf(99.99), result.price());

        verify(productRepository).findById(productId);
    }

    @Test
    public void testGetProductById_NotFound() {
        UUID nonExistingProductId = UUID.randomUUID();
        when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(nonExistingProductId));

        verify(productRepository).findById(nonExistingProductId);
    }

    @Test
    public void testGetProductsBySellerId_Success() {
        List<Product> products = new ArrayList<>(Arrays.asList(product));
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        when(productRepository.findBySellerId(sellerId)).thenReturn(products);
        when(productMapper.toDto(product)).thenReturn(new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        ));

        List<ProductDTO> result = productService.getProductsBySellerId(sellerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).name());

        verify(sellerRepository).findById(sellerId);
        verify(productRepository).findBySellerId(sellerId);
    }

    @Test
    public void testGetProductsBySellerId_SellerNotFound() {
        UUID nonExistingSellerId = UUID.randomUUID();
        when(sellerRepository.findById(nonExistingSellerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductsBySellerId(nonExistingSellerId));

        verify(sellerRepository).findById(nonExistingSellerId);
        verify(productRepository, never()).findBySellerId(any());
    }
}
