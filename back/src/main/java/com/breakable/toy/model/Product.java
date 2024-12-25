package com.breakable.toy.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class Product {
    private String id;
    private String name;
    private String category;
    private double unitPrice;
    private Optional<LocalDateTime> expirationDate;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private Integer quantityInStock;

    private Product(String id, String name, String category, double unitPrice, Optional<LocalDateTime> expirationDate2,
            LocalDateTime creationDate2, LocalDateTime updateDate2, Integer quantityInStock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.expirationDate = expirationDate2;
        this.creationDate = creationDate2;
        this.updateDate = updateDate2;
        this.quantityInStock = quantityInStock;
    }

    public Product(String name, String category, double unitPrice, Optional<LocalDateTime> expirationDate,
            LocalDateTime creationDate, LocalDateTime updateDate, Integer quantityInStock) {
        this(UUID.randomUUID().toString(), name, category, unitPrice, expirationDate, creationDate, updateDate,
                quantityInStock);
    }

    public Product() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setExpirationDate(Optional<LocalDateTime> expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public Optional<LocalDateTime> getExpirationDate() {
        return expirationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public Boolean fieldsAreValid() {
        return (Stream.of(this.id, this.category, this.name, this.quantityInStock, this.unitPrice)
                .allMatch(Objects::nonNull)) && this.name.length() <= 120;
    }

}