package com.breakable.toy.controller;

import com.breakable.toy.model.*;
import com.breakable.toy.model.Result;
import com.breakable.toy.model.Result.Status;
import com.breakable.toy.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;

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
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    ResponseEntity<Iterable<Product>> getProducts(@RequestParam String name,
            @RequestParam ArrayList<String> categories, @RequestParam String availability) {
        Iterable<Product> products = productService.getFilteredElements(name, categories, availability);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Gets the list of all available categories.
    @GetMapping("/categories")
    ResponseEntity<Iterable<String>> getCategoriesList() {
        return new ResponseEntity<>(productService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<HashMap<String, Statistics>> getStatistics() {
        return new ResponseEntity<>(productService.getStatistics(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Result<Product>> postProduct(@RequestBody Product product) {
        // Check if product fields are valid
        if (product.fieldsAreValid()) {
            Product created_product = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<Product>(Status.Ok, "Sucessfully created product", created_product));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result<Product>(Status.Err, "Product fields are invalid", product));
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Result<Product>> putProduct(@PathVariable String id, @RequestBody Product product) {
        // Check if product already exsits
        if (productService.containsProduct(id)) {

            Product updatedProduct = productService.updateProduct(product);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<Product>(Status.Ok, "Successfully updated product", updatedProduct));
        } else {
            // If it doesnt exists we create it.
            return this.postProduct(product);
        }
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
