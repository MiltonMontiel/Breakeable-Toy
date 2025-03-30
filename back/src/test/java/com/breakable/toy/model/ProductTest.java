package com.breakable.toy.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void testValidProductCreation() {
        Product product = new Product("Test Product", "Test Category", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        product.setId();

        assertTrue(product.fieldsAreValid());
        assertNotNull(product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Category", product.getCategory());
        assertEquals(10.0, product.getUnitPrice());
        assertEquals(100, product.getQuantityInStock());
    }

    @Test
    void testInvalidProductCreation() {
        Product product = new Product("", "", -1.0,
                Optional.empty(), LocalDateTime.now(), LocalDateTime.now(), -1);
        product.setId();

        assertFalse(product.fieldsAreValid());
    }

    @Test
    void testProductWithNullValues() {
        Product product = new Product();
        product.setId();

        assertFalse(product.fieldsAreValid());
    }

    @Test
    void testProductWithLongName() {
        String longName = "a".repeat(121);
        Product product = new Product(longName, "Test Category", 10.0,
                Optional.of(LocalDateTime.now()), LocalDateTime.now(), LocalDateTime.now(), 100);
        product.setId();

        assertFalse(product.fieldsAreValid());
    }
} 