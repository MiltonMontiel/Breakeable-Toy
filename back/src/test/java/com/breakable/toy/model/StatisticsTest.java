package com.breakable.toy.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class StatisticsTest {

    @Test
    void testStatisticsCreation() {
        Product product = new Product("Test Product", "Test Category", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        
        Statistics stats = new Statistics(product);
        
        assertEquals(100, stats.getTotalProductsInStock());
        assertEquals(1000.0, stats.getTotalValueInStock());
        assertEquals(10.0, stats.getAveragePriceInStock());
    }

    @Test
    void testAddProduct() {
        Product product1 = new Product("Test Product 1", "Test Category", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        
        Product product2 = new Product("Test Product 2", "Test Category", 20.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 50);
        
        Statistics stats = new Statistics(product1);
        stats.addProduct(product2);
        
        assertEquals(150, stats.getTotalProductsInStock());
        assertEquals(2000.0, stats.getTotalValueInStock());
        assertEquals(13.33, stats.getAveragePriceInStock(), 0.01);
    }

    @Test
    void testRemoveProduct() {
        Product product1 = new Product("Test Product 1", "Test Category", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        
        Product product2 = new Product("Test Product 2", "Test Category", 20.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 50);
        
        Statistics stats = new Statistics(product1);
        stats.addProduct(product2);
        stats.removeProduct(product1);
        
        assertEquals(50, stats.getTotalProductsInStock());
        assertEquals(1000.0, stats.getTotalValueInStock());
        assertEquals(20.0, stats.getAveragePriceInStock());
    }

    @Test
    void testZeroProducts() {
        Product product = new Product("Test Product", "Test Category", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 0);
        
        Statistics stats = new Statistics(product);
        stats.removeProduct(product);
        
        assertEquals(0, stats.getTotalProductsInStock());
        assertEquals(0.0, stats.getTotalValueInStock());
        assertEquals(0.0, stats.getAveragePriceInStock());
    }
} 