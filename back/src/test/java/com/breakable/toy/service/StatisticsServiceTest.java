package com.breakable.toy.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.breakable.toy.model.Product;
import com.breakable.toy.model.Statistics;

class StatisticsServiceTest {

    private StatisticsService statisticsService;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        statisticsService = new StatisticsService();
        testProduct = new Product("Test Product", "Category A", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        testProduct.setId();
    }

    @Test
    void testUpdateStats() {
        statisticsService.updateStats(testProduct);

        var stats = statisticsService.getStatistics();
        assertEquals(1, stats.size());
        assertTrue(stats.containsKey("Category A"));

        Statistics categoryStats = stats.get("Category A");
        assertEquals(100, categoryStats.getTotalProductsInStock());
        assertEquals(1000.0, categoryStats.getTotalValueInStock());
        assertEquals(10.0, categoryStats.getAveragePriceInStock());
    }

    @Test
    void testUpdateStatsMultipleProducts() {
        statisticsService.updateStats(testProduct);

        Product secondProduct = new Product("Another Product", "Category A", 20.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 50);
        secondProduct.setId();
        statisticsService.updateStats(secondProduct);

        var stats = statisticsService.getStatistics();
        Statistics categoryStats = stats.get("Category A");
        assertEquals(150, categoryStats.getTotalProductsInStock());
        assertEquals(2000.0, categoryStats.getTotalValueInStock());
        assertEquals(13.33, categoryStats.getAveragePriceInStock(), 0.01);
    }

    @Test
    void testRemoveProduct() {
        statisticsService.updateStats(testProduct);
        statisticsService.removeProduct(testProduct);

        var stats = statisticsService.getStatistics();
        assertFalse(stats.containsKey("Category A"));
    }

    @Test
    void testMultipleCategories() {
        statisticsService.updateStats(testProduct);

        Product otherProduct = new Product("Other Product", "Category B", 15.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        otherProduct.setId();
        statisticsService.updateStats(otherProduct);

        var stats = statisticsService.getStatistics();
        assertEquals(2, stats.size());

        Statistics categoryAStats = stats.get("Category A");
        assertEquals(100, categoryAStats.getTotalProductsInStock());
        assertEquals(1000.0, categoryAStats.getTotalValueInStock());
        assertEquals(10.0, categoryAStats.getAveragePriceInStock());

        Statistics categoryBStats = stats.get("Category B");
        assertEquals(100, categoryBStats.getTotalProductsInStock());
        assertEquals(1500.0, categoryBStats.getTotalValueInStock());
        assertEquals(15.0, categoryBStats.getAveragePriceInStock());
    }

    @Test
    void testGetStatisticsReturnsCopy() {
        statisticsService.updateStats(testProduct);
        var stats1 = statisticsService.getStatistics();
        var stats2 = statisticsService.getStatistics();

        assertNotSame(stats1, stats2);
        assertEquals(stats1, stats2);
    }
} 