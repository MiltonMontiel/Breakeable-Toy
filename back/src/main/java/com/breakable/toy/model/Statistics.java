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
        if (product == null || product.getQuantityInStock() == null) {
            return;
        }
        
        int quantity = product.getQuantityInStock();
        double price = product.getUnitPrice();
        
        if (quantity > 0) {
            this.totalProductsInStock += quantity;
            this.totalValueInStock += price * quantity;
        }
    }

    public void removeProduct(Product product) {
        if (product == null || product.getQuantityInStock() == null) {
            return;
        }
        
        int quantity = product.getQuantityInStock();
        double price = product.getUnitPrice();
        
        this.totalProductsInStock -= quantity;
        this.totalValueInStock -= price * quantity;
        
        // Ensure we don't have negative values
        if (this.totalProductsInStock < 0) {
            this.totalProductsInStock = 0;
        }
        
        if (this.totalValueInStock < 0) {
            this.totalValueInStock = 0.0;
        }
    }

    /**
     * Update the statistics by removing the old product values and adding the new ones.
     * This is useful for product updates where fields have changed.
     * 
     * @param oldProduct The product with its original values
     * @param newProduct The product with updated values
     */
    public void updateProduct(Product oldProduct, Product newProduct) {
        removeProduct(oldProduct);
        addProduct(newProduct);
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
