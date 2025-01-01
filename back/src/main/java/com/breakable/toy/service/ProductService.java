package com.breakable.toy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.breakable.toy.model.Product;
import com.breakable.toy.model.Statistics;

@Service
public class ProductService {

    private Set<String> categories = new HashSet<String>();
    private ArrayList<Product> products = new ArrayList<Product>();
    private Set<String> ids = new HashSet<String>();
    private HashMap<String, Statistics> statistics = new HashMap<>();

    public ProductService() {
        this.createProduct(
                new Product("Bananas", "Food", 10.2, Optional.empty(), LocalDateTime.now(), LocalDateTime.now(), 123));
        this.createProduct(
                new Product("Car", "Something", 1000.2, Optional.empty(), LocalDateTime.now(), LocalDateTime.now(),
                        123));

        for (Product p : this.products) {
            this.categories.add(p.getCategory());
            this.ids.add(p.getId());
        }

    }

    public Iterable<Product> getAllProducts() {
        return this.products;
    }

    public Iterable<String> getCategories() {
        return this.categories;
    }

    public boolean containsProduct(String id) {
        return this.ids.contains(id);
    }

    public Product createProduct(Product product) {
        this.categories.add(product.getCategory());
        product.setId();
        this.ids.add(product.getId());
        product.setCreationDate(LocalDateTime.now());
        product.setUpdateDate(LocalDateTime.now());
        this.products.add(product);

        // Updating stats
        updateStats(product);

        return product;
    }

    public void updateStats(Product product) {
        if (this.statistics.containsKey(product.getCategory())) {
            statistics.get(product.getCategory()).addProduct(product);
        } else {
            statistics.put(product.getCategory(), new Statistics(product));
        }
    }

    public Product updateProduct(Product product) {
        int productIndex = -1;

        for (Product p : this.products) {
            if (p.getId().equals(product.getId())) {
                // Removes the past product from the statistics.
                statistics.get(product.getCategory()).removeProduct(p);
                productIndex = this.products.indexOf(p);
                // Adds the updated product to the statistics.
                updateStats(product);
                this.products.set(productIndex, product);
            }
        }

        return product;
    }

    public HashMap<String, Statistics> getStatistics() {
        return this.statistics;
    }

    public List<Product> getFilteredElements(String name, ArrayList<String> categories, String availiability) {
        return this.products.stream()
                .filter(product -> (name == null || product.getName().contains(name)))
                .filter(product -> (categories.isEmpty() ||
                        categories.contains(product.getCategory())))
                .filter(product -> (availiability == null || this.matchesAvailability(availiability, product)))
                .collect(Collectors.toList());
    }

    private boolean matchesAvailability(String availability, Product product) {
        switch (availability) {
            case "In Stock":
                return product.getQuantityInStock() > 0;
            case "Out Of Stock":
                return product.getQuantityInStock() == 0;
            default:
                return true;
        }
    }
}
