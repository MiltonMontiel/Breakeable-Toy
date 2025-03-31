package com.breakable.toy.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.breakable.toy.model.Product;
import com.breakable.toy.model.Statistics;

@Service
public class StatisticsService {
    private final Map<String, Statistics> statistics = new HashMap<>();

    public void updateStats(Product product) {
        if (product == null || product.getQuantityInStock() == null) {
            return;
        }
        
        String category = product.getCategory();
        if (category == null || category.isEmpty()) {
            return;
        }
        
        Statistics stats = statistics.computeIfAbsent(category, k -> new Statistics());
        stats.addProduct(product);
        
        // Handle edge case where statistics might be zero after adding
        if (stats.getTotalProductsInStock() <= 0) {
            statistics.remove(category);
        }
    }

    public void removeProduct(Product product) {
        if (product == null || product.getQuantityInStock() == null) {
            return;
        }
        
        String category = product.getCategory();
        if (category == null || category.isEmpty()) {
            return;
        }
        
        if (statistics.containsKey(category)) {
            Statistics stats = statistics.get(category);
            stats.removeProduct(product);
            
            // Clean up categories with no products
            if (stats.getTotalProductsInStock() <= 0) {
                statistics.remove(category);
            }
        }
    }

    /**
     * Update statistics when a product is modified but stays in the same category
     * @param oldProduct The product before changes
     * @param newProduct The product after changes
     */
    public void updateProductStats(Product oldProduct, Product newProduct) {
        if (oldProduct == null || newProduct == null || 
            !oldProduct.getCategory().equals(newProduct.getCategory())) {
            return;
        }
        
        String category = newProduct.getCategory();
        Statistics stats = statistics.computeIfAbsent(category, k -> new Statistics());
        stats.updateProduct(oldProduct, newProduct);
        
        // Clean up if needed
        if (stats.getTotalProductsInStock() <= 0) {
            statistics.remove(category);
        }
    }

    /**
     * Update statistics when a product's category changes
     * @param oldCategory The product's previous category
     * @param updatedProduct The product with updated information
     */
    public void updateProductCategory(String oldCategory, Product updatedProduct) {
        // First remove from old category
        if (oldCategory != null && !oldCategory.isEmpty() && statistics.containsKey(oldCategory)) {
            Statistics oldStats = statistics.get(oldCategory);
            oldStats.removeProduct(updatedProduct);
            
            // Clean up categories with no products
            if (oldStats.getTotalProductsInStock() <= 0) {
                statistics.remove(oldCategory);
            }
        }
        
        // Then add to new category
        updateStats(updatedProduct);
    }

    public Map<String, Statistics> getStatistics() {
        return new HashMap<>(statistics);
    }
} 