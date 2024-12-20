package com.breakable.toy;

// TODO: Usar localdatime
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

class Product {
    private String id;
    private String name;
    private String category;
    private double unitPrice;
    private Optional<Date> expirationDate;
    private Date creationDate;
    private Date updateDate;
    private Integer quantityInStock;
    private boolean inStock;

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public Product(String id, String name, String category, double unitPrice, Optional<Date> expirationDate2,
            Date creationDate2, Date updateDate2, Integer quantityInStock, boolean inStock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.expirationDate = expirationDate2;
        this.creationDate = creationDate2;
        this.updateDate = updateDate2;
        this.quantityInStock = quantityInStock;
    }

    public Product(String name, String category, double unitPrice, Optional<Date> expirationDate,
            Date creationDate, Date updateDate, Integer quantityInStock, boolean inStock) {
        this(UUID.randomUUID().toString(), name, category, unitPrice, expirationDate, creationDate, updateDate,
                quantityInStock, inStock);
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

    public void setExpirationDate(Optional<Date> expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(Date updateDate) {
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

    public Optional<Date> getExpirationDate() {
        return expirationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

}