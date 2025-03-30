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
        String category = product.getCategory();
        Statistics stats = statistics.computeIfAbsent(category, k -> new Statistics());
        stats.addProduct(product);
    }

    public void removeProduct(Product product) {
        String category = product.getCategory();
        if (statistics.containsKey(category)) {
            Statistics stats = statistics.get(category);
            stats.removeProduct(product);
            if (stats.getTotalProductsInStock() <= 0) {
                statistics.remove(category);
            }
        }
    }

    public Map<String, Statistics> getStatistics() {
        return new HashMap<>(statistics);
    }
} 