package com.breakable.toy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
                new Product("Test", "Cat", 12.2, new Date(), new Date(), new Date(), 2, true),
                new Product("Test 1", "Cat 1", 123.34, new Date(), new Date(), new Date(), 23, false)));

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
        products.add(product);
        categories.add(product.getCategory());
        return product;
    }

    // update a product (name, category, price, stock, expiration date)
    @PutMapping("/{id}")
    Product putProduct(@PathVariable String id, @RequestBody Product product) {
        int productIndex = -1;

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

    // Given an id, if there is a product with such an id then
    // this sets the inStock attribute to false, otherwise it returns
    // null.

    // TODO: Adapt this to the IETF documentation.
    @PutMapping("/{id}/outofstock")
    public Optional<Product> putOutOfStock(@PathVariable String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setInStock(false);
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    // Given an id, if there is a product matching such id then
    // this sets the inStock attribute to true. Otherwise it returns
    // null.

    // TODO: Adapt this to the IETF documentation.
    @PutMapping("/{id}/instock")
    public Optional<Product> putInStock(@PathVariable String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setInStock(true);
                return Optional.of(p);
            }
        }

        return Optional.empty();
    }

}
