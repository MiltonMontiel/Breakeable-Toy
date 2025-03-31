package com.breakable.toy.controller;

import com.breakable.toy.model.*;
import com.breakable.toy.model.Result;
import com.breakable.toy.model.Result.Status;
import com.breakable.toy.service.ProductService;
import com.breakable.toy.service.StatisticsService;
import com.breakable.toy.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final StatisticsService statisticsService;

    @Autowired
    public ProductController(ProductService productService, StatisticsService statisticsService) {
        this.productService = productService;
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) String availability) {
        return ResponseEntity.ok(productService.getFilteredProducts(name, categories, availability));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(productService.getCategories().stream().toList());
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Statistics>> getStatistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Result<Product>> createProduct(@RequestBody Product product) {
        if (!product.fieldsAreValid()) {
            return ResponseEntity.badRequest()
                    .body(new Result<>(Status.Err, "Product fields are invalid", product));
        }
        
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(new Result<>(Status.Ok, "Successfully created product", createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Product>> updateProduct(
            @PathVariable String id,
            @RequestBody Product product) {
        if (!product.fieldsAreValid()) {
            return ResponseEntity.badRequest()
                    .body(new Result<>(Status.Err, "Product fields are invalid", product));
        }

        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(new Result<>(Status.Ok, "Successfully updated product", updatedProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(Status.Err, "Product not found", product));
        }
    }

    @PutMapping("/{id}/out-of-stock")
    public ResponseEntity<Result<Product>> setOutOfStock(@PathVariable String id) {
        try {
            Product result = productService.setProductOutOfStock(id);
            return ResponseEntity.ok(new Result<>(Status.Ok, "Product set to out of stock", result));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(Status.Err, "Product not found", null));
        }
    }

    @PutMapping("/{id}/instock")
    public ResponseEntity<Result<Product>> setInStock(
            @PathVariable String id,
            @RequestBody Product product) {
        try {
            if (product.getQuantityInStock() <= 0) {
                return ResponseEntity.badRequest()
                        .body(new Result<>(Status.Err, "Quantity must be greater than zero", product));
            }
            
            Product result = productService.setProductStockLevel(id, product.getQuantityInStock());
            return ResponseEntity.ok(new Result<>(Status.Ok, "Product stock updated", result));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(Status.Err, "Product not found", product));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new Result<>(Status.Err, e.getMessage(), product));
        }
    }
}
