package com.breakable.toy.controller;

import com.breakable.toy.model.*;
import com.breakable.toy.model.Result;
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
        if (productService.containsProduct(id)) {
            Product retrievedProduct = productService.getProductById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result<Product>(Status.Ok, "Product retrieved correctly", retrievedProduct));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<Product>(Status.Err, "Product with id: " + id + " not found", null));
        }
    }

    // Gets the list of all available categories.
    @GetMapping("/categories")
    ResponseEntity<Iterable<String>> getCategoriesList() {
        return new ResponseEntity<>(productService.getCategories(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Result<Product>> postProduct(@RequestBody Product product) {
        // Check if product fields are valid
        if (product.fieldsAreValid()) {
            // Check if product already exists.
            if (!productService.containsProduct(product.getId())) {
                Product created_product = productService.createProduct(product);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Result<Product>(Status.Ok, "Sucessfully created product", created_product));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Result<Product>(Status.Err, "Product already exists", product));
            }

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
