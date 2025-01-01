package com.breakable.toy.model;

public class Statistics {
    int totalProductsInStock;
    double totalValueInStock;
    double averagePriceInStock;

    public int getTotalProductsInStock() {
        return totalProductsInStock;
    }

    public double getTotalValueInStock() {
        return totalValueInStock;
    }

    public double getAveragePriceInStock() {
        return averagePriceInStock;
    }

    public Statistics(Product product) {
        this.totalProductsInStock = product.getQuantityInStock();
        this.totalValueInStock = product.getUnitPrice() * product.getQuantityInStock();
        this.averagePriceInStock = this.totalValueInStock / this.totalProductsInStock;
    }

    public void addProduct(Product product) {
        this.totalProductsInStock += product.getQuantityInStock();
        this.totalValueInStock += product.getUnitPrice() * product.getQuantityInStock();
        this.averagePriceInStock = this.totalValueInStock / this.totalProductsInStock;
    }

    public void removeProduct(Product product) {
        this.totalProductsInStock -= product.getQuantityInStock();
        this.totalValueInStock -= product.getUnitPrice() * product.getQuantityInStock();
        this.averagePriceInStock = this.totalValueInStock / this.totalProductsInStock;
    }
}
