package com.breakable.toy.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.breakable.toy.model.Product;
import com.breakable.toy.model.Result;
import com.breakable.toy.model.Result.Status;

@Service
public class ProductService {

    private Set<String> categories = new HashSet<String>();
    private HashMap<String, Product> productsMap = new HashMap<>();
    private Collection<Product> products = this.productsMap.values();

    public ProductService() {
        this.createProduct(
                new Product("Bananas", "Food", 10.2, Optional.empty(), LocalDateTime.now(), LocalDateTime.now(), 123));
        this.createProduct(
                new Product("Car", "Something", 1000.2, Optional.empty(), LocalDateTime.now(), LocalDateTime.now(),
                        123));

        for (Product p : this.productsMap.values()) {
            this.categories.add(p.getCategory());
        }
    }

    public Iterable<Product> getAllProducts() {
        return this.products;
    }

    public Iterable<String> getCategories() {
        return this.categories;
    }

    public Product getProductById(String id) {
        return this.productsMap.get(id);
    }

    public boolean containsProduct(String id) {
        return this.productsMap.containsKey(id);
    }

    public Product createProduct(Product product) {
        this.categories.add(product.getCategory());
        product.setCreationDate(LocalDateTime.now());
        product.setUpdateDate(LocalDateTime.now());

        this.productsMap.put(product.getId(), product);
        this.categories.add(product.getCategory());
        return product;
    }

    public Product updateProduct(Product product) {
        this.productsMap.put(product.getId(), product);
        return product;
    }
}
