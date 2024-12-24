package com.breakable.toy.controller;

import java.util.Optional;
import com.breakable.toy.model.*;
import com.breakable.toy.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // List products
    @GetMapping
    ResponseEntity<Iterable<Product>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {

        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Gets the list of all available categories.
    @GetMapping("/categories")
    ResponseEntity<Iterable<String>> getCategoriesList() {
        return new ResponseEntity<>(productService.getCategories(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Product> postProduct(@RequestBody Product product) {
        Optional<Product> created_product = productService.createProduct(product);

        return (created_product.isEmpty()) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(created_product.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Product> putProduct(@PathVariable String id, @RequestBody Product product) {
        Optional<Product> updated_product = productService.updateProduct(product);

        return (updated_product.isEmpty()) ? this.postProduct(product)
                : new ResponseEntity<>(updated_product.get(), HttpStatus.OK);

    }

    @PutMapping("/{id}/outofstock")
    ResponseEntity<Product> putOutOfStock(@PathVariable String id, @RequestBody Product product) {
        if (product.getQuantityInStock() == 0) {
            return this.putProduct(id, product);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/instock")
    ResponseEntity<Product> putInStock(@PathVariable String id, @RequestBody Product product) {
        if (product.getQuantityInStock() > 0) {
            return this.putProduct(id, product);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
