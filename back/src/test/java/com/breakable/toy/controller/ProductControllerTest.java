package com.breakable.toy.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.breakable.toy.exception.ResourceNotFoundException;
import com.breakable.toy.model.Product;
import com.breakable.toy.model.Result;
import com.breakable.toy.model.Result.Status;
import com.breakable.toy.model.Statistics;
import com.breakable.toy.service.ProductService;
import com.breakable.toy.service.StatisticsService;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private ProductController productController;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product("Test Product", "Test Category", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        testProduct.setId();
    }

    @Test
    void testGetProducts() {
        List<Product> products = List.of(testProduct);
        when(productService.getFilteredProducts(any(), any(), any())).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getProducts("Test", List.of(), null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    void testGetCategories() {
        List<String> categories = List.of("Test Category");
        when(productService.getCategories()).thenReturn(categories.stream().collect(Collectors.toSet()));

        ResponseEntity<List<String>> response = productController.getCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
    }

    @Test
    void testGetStatistics() {
        Map<String, Statistics> stats = Map.of("Test Category", new Statistics(testProduct));
        when(statisticsService.getStatistics()).thenReturn(stats);

        ResponseEntity<Map<String, Statistics>> response = productController.getStatistics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stats, response.getBody());
    }

    @Test
    void testDeleteProduct() {
        ResponseEntity<Void> response = productController.deleteProduct("test-id");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService).deleteProduct("test-id");
    }

    @Test
    void testCreateValidProduct() {
        when(productService.createProduct(any())).thenReturn(testProduct);

        ResponseEntity<Result<Product>> response = productController.createProduct(testProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Status.Ok, response.getBody().status());
        assertEquals(testProduct, response.getBody().data());
    }

    @Test
    void testCreateInvalidProduct() {
        Product invalidProduct = new Product();
        ResponseEntity<Result<Product>> response = productController.createProduct(invalidProduct);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Status.Err, response.getBody().status());
        assertEquals(invalidProduct, response.getBody().data());
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(any(), any())).thenReturn(testProduct);

        ResponseEntity<Result<Product>> response = productController.updateProduct("test-id", testProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Status.Ok, response.getBody().status());
        assertEquals(testProduct, response.getBody().data());
    }

    @Test
    void testUpdateProductNotFound() {
        when(productService.updateProduct(any(), any()))
                .thenThrow(new ResourceNotFoundException("Product not found"));

        ResponseEntity<Result<Product>> response = productController.updateProduct("test-id", testProduct);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Status.Err, response.getBody().status());
        assertEquals(testProduct, response.getBody().data());
    }

    @Test
    void testSetOutOfStock() {
        when(productService.getProductById(testProduct.getId())).thenReturn(testProduct);
        when(productService.updateProduct(eq(testProduct.getId()), any(Product.class)))
                .thenReturn(testProduct);

        ResponseEntity<Result<Product>> response = productController.setOutOfStock(testProduct.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Status.Ok, response.getBody().status());
        assertEquals(0, response.getBody().data().getQuantityInStock());
        verify(productService).updateProduct(eq(testProduct.getId()), any(Product.class));
    }

    @Test
    void testSetInStock() {
        when(productService.updateProduct(any(), any())).thenReturn(testProduct);

        ResponseEntity<Result<Product>> response = productController.setInStock("test-id", testProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Status.Ok, response.getBody().status());
        assertEquals(testProduct, response.getBody().data());
    }

    @Test
    void testSetInStockInvalidQuantity() {
        testProduct.setQuantityInStock(0);
        ResponseEntity<Result<Product>> response = productController.setInStock("test-id", testProduct);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Status.Err, response.getBody().status());
        assertEquals(testProduct, response.getBody().data());
    }
} 