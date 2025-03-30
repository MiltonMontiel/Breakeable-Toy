package com.breakable.toy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breakable.toy.exception.ResourceNotFoundException;
import com.breakable.toy.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private StatisticsService statisticsService;

    private ProductService productService;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        productService = new ProductService(statisticsService);
        testProduct = new Product("Test Product", "Test Category", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        testProduct.setId();
    }

    @Test
    void testCreateProduct() {
        Product product = productService.createProduct(testProduct);

        assertNotNull(product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Category", product.getCategory());
        assertEquals(10.0, product.getUnitPrice());
        assertEquals(100, product.getQuantityInStock());
        verify(statisticsService).updateStats(product);
    }

    @Test
    void testCreateInvalidProduct() {
        Product invalidProduct = new Product();
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(invalidProduct));
    }

    @Test
    void testUpdateProduct() {
        Product createdProduct = productService.createProduct(testProduct);
        LocalDateTime originalUpdateDate = createdProduct.getUpdateDate();

        Product updatedProduct = new Product("Updated Product", "Updated Category", 20.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 200);

        Product result = productService.updateProduct(createdProduct.getId(), updatedProduct);

        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Category", result.getCategory());
        assertEquals(20.0, result.getUnitPrice());
        assertEquals(200, result.getQuantityInStock());
        assertNotEquals(originalUpdateDate, result.getUpdateDate());
        verify(statisticsService, times(2)).updateStats(any(Product.class));
    }

    @Test
    void testUpdateNonExistentProduct() {
        assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct("non-existent-id", testProduct));
    }

    @Test
    void testDeleteProduct() {
        Product createdProduct = productService.createProduct(testProduct);
        productService.deleteProduct(createdProduct.getId());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.getProductById(createdProduct.getId()));
        verify(statisticsService).removeProduct(createdProduct);
    }

    @Test
    void testDeleteNonExistentProduct() {
        assertThrows(ResourceNotFoundException.class,
                () -> productService.deleteProduct("non-existent-id"));
    }

    @Test
    void testGetFilteredProducts() {
        Product product1 = new Product("Apple", "Fruit", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        Product product2 = new Product("Banana", "Fruit", 15.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 0);

        productService.createProduct(product1);
        productService.createProduct(product2);

        List<String> categories = List.of("Fruit");
        List<Product> filteredProducts = productService.getFilteredProducts("a", categories, "in stock");

        assertEquals(1, filteredProducts.size());
        assertEquals("Apple", filteredProducts.get(0).getName());
    }

    @Test
    void testGetCategories() {
        productService = new ProductService(statisticsService);
        Product product1 = new Product("Apple", "Fruit", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        Product product2 = new Product("Carrot", "Vegetable", 15.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);

        productService.createProduct(product1);
        productService.createProduct(product2);

        assertEquals(2, productService.getCategories().size());
        verify(statisticsService, times(2)).updateStats(any(Product.class));
    }

    @Test
    void testExistsById() {
        Product createdProduct = productService.createProduct(testProduct);
        assertTrue(productService.existsById(createdProduct.getId()));
        assertFalse(productService.existsById("non-existent-id"));
    }
} 