package com.breakable.toy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@SpringBootApplication
public class Backend {
    public static void main(String[] args) {
        SpringApplication.run(Backend.class, args);
    }
}

@RestController
@RequestMapping("/products")
class RestAPIController {
    private List<Product> products = new ArrayList<Product>();
    private Set<String> categories = new HashSet<String>();

    public RestAPIController() {
        products.addAll(List.of(
                new Product("Watermelon", "Food", 123.2, Optional.empty(), LocalDateTime.now(), LocalDateTime.now(), 2,
                        true),
                new Product("Apple", "Fruit", 123.34, Optional.empty(), LocalDateTime.now(), LocalDateTime.now(), 23,
                        false)));

        // Populate categories list
        for (Product p : products) {
            categories.add(p.getCategory());
        }
    }

    // List products
    @GetMapping
    Iterable<Product> getProducts() {
        return products;
    }

    // Get a product by id
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return Optional.of(p);
            }
        }

        return Optional.empty();
    }

    // Gets the list of all available categories.
    @GetMapping("/categories")
    Iterable<String> getMethodName() {
        return categories;
    }

    // Create a new product with validation.
    @PostMapping
    Product postProduct(@RequestBody Product product) {
        // TODO: Check if required fields are present.
        if (product.fieldsAreValid()) {

            // TODO: Check if product.name has <= 120 characters.

            // Set creation date to now.
            LocalDateTime creationDate = LocalDateTime.now();
            product.setCreationDate(creationDate);

            products.add(product);
            categories.add(product.getCategory());
            return product;
        } else {
            return product;
        }
    }

    // update a product (name, category, price, stock, expiration date)
    @PutMapping("/{id}")
    Product putProduct(@PathVariable String id, @RequestBody Product product) {
        int productIndex = -1;
        LocalDateTime updateDate = LocalDateTime.now();
        product.setUpdateDate(updateDate);

        // TODO: Do this in constant time.
        // Check if product already exists.
        for (Product p : products) {
            if (p.getId().equals(id)) {
                productIndex = products.indexOf(p);
                products.set(productIndex, product);
                categories.add(p.getCategory());
            }
        }

        // If the product doesnt exists then we create it.
        return (productIndex == -1) ? postProduct(product) : product;
    }

    /*
     * Given an id and a product, this sets the quantity in stock of such product to
     * zero
     * and then checks if it exists in the products list. If it does then it updates
     * it
     * with the new product, otherwise it creates it.
     */
    @PutMapping("/{id}/outofstock")
    ResponseEntity<Product> putOutOfStock(@PathVariable String id, @RequestBody Product product) {
        int productIndex = -1;

        // Update the number of items in stock to zero
        product.setQuantityInStock(0);

        for (Product p : products) {
            if (p.getId().equals(id)) {
                productIndex = products.indexOf(p);
                products.set(productIndex, product);
            }
        }

        return (productIndex == -1) ? new ResponseEntity<>(postProduct(product), HttpStatus.CREATED)
                : new ResponseEntity<>(product, HttpStatus.OK);
    }

    /*
     * Given an id and a product, this sets the quantity in stock of such product to
     * zero
     * and then checks if it exists in the products list. If it does then it updates
     * it
     * with the new product, otherwise it creates it.
     */
    @PutMapping("/{id}/instock")
    ResponseEntity<Product> putInStock(@PathVariable String id, @RequestBody Product product) {
        int productIndex = -1;

        for (Product p : products) {
            if (p.getId().equals(id)) {
                productIndex = products.indexOf(p);
                products.set(productIndex, product);
            }
        }

        return (productIndex == -1) ? new ResponseEntity<>(postProduct(product), HttpStatus.CREATED)
                : new ResponseEntity<>(product, HttpStatus.OK);

    }
}
