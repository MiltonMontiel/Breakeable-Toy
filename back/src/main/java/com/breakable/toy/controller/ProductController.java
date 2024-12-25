package com.breakable.toy.controller;

import java.util.Optional;
import com.breakable.toy.model.*;
import com.breakable.toy.model.Result.Status;
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
    public ResponseEntity<Result<Product>> getProductById(@PathVariable String id) {

        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            Result<Product> result = new Result<Product>(Status.Ok, "Product retrieved correctly", null);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            Result<Product> result = new Result<>(Status.Err, "Product with id " + id + "not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    // Gets the list of all available categories.
    @GetMapping("/categories")
    ResponseEntity<Iterable<String>> getCategoriesList() {
        return new ResponseEntity<>(productService.getCategories(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Result<Product>> postProduct(@RequestBody Product product) {
        Result<Product> created_product = productService.createProduct(product);

        return (created_product.status().equals(Status.Err))
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(created_product)
                : ResponseEntity.status(HttpStatus.OK).body(created_product);
    }

    @PutMapping("/{id}")
    ResponseEntity<Result<Product>> putProduct(@PathVariable String id, @RequestBody Product product) {
        Result<Product> updated_product = productService.updateProduct(product);

        return (updated_product.status().equals(Status.Err)) ? this.postProduct(product)
                : ResponseEntity.status(HttpStatus.OK).body(updated_product);

    }

    @PutMapping("/{id}/outofstock")
    ResponseEntity<Result<Product>> putOutOfStock(@PathVariable String id, @RequestBody Product product) {
        product.setQuantityInStock(0);
        return this.putProduct(id, product);
    }

    @PutMapping("/{id}/instock")
    ResponseEntity<Result<Product>> putInStock(@PathVariable String id, @RequestBody Product product) {
        if (product.getQuantityInStock() > 0) {
            return this.putProduct(id, product);
        } else {
            Result<Product> result = new Result<Product>(Status.Err,
                    "Please ensure quantityInStock is grater than zero", product);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}
