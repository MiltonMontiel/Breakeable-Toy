package com.breakable.toy.model;

public class Statistics {
    private int totalProductsInStock;
    private double totalValueInStock;

    public int getTotalProductsInStock() {
        return totalProductsInStock;
    }

    public double getTotalValueInStock() {
        return totalValueInStock;
    }

    public Double getAveragePriceInStock() {
        if (totalProductsInStock == 0) {
            return 0.0;
        }
        return totalValueInStock / totalProductsInStock;
    }

    public Statistics() {
        this.totalProductsInStock = 0;
        this.totalValueInStock = 0.0;
    }

    public Statistics(Product product) {
        this.totalProductsInStock = product.getQuantityInStock();
        this.totalValueInStock = product.getUnitPrice() * product.getQuantityInStock();
    }

    public void addProduct(Product product) {
        this.totalProductsInStock += product.getQuantityInStock();
        this.totalValueInStock += product.getUnitPrice() * product.getQuantityInStock();
    }

    public void removeProduct(Product product) {
        this.totalProductsInStock -= product.getQuantityInStock();
        this.totalValueInStock -= product.getUnitPrice() * product.getQuantityInStock();
        if (this.totalProductsInStock < 0) {
            this.totalProductsInStock = 0;
            this.totalValueInStock = 0.0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Statistics that = (Statistics) obj;
        return totalProductsInStock == that.totalProductsInStock &&
                Double.compare(totalValueInStock, that.totalValueInStock) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * totalProductsInStock + Double.hashCode(totalValueInStock);
    }
}
