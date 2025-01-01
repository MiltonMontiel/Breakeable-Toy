package com.breakable.toy.model;

import java.util.ArrayList;

public class Filter {
    private String name;
    private ArrayList<String> category;
    private String availability;

    private boolean matchesName(Product product) {
        if (this.name == null) {
            return true;
        } else {
            return product.getName().contains(this.name);
        }
    }

    private boolean matchesCategory(Product product) {

        for (String category : this.category) {
            if (product.getCategory().equals(category)) {
                return true;
            }
        }

        return false;
    }

    private boolean matchesAvailability(Product product) {
        switch (this.availability) {
            case "In Stock":
                return product.getQuantityInStock() > 0;
            case "Out Of Stock":
                return product.getQuantityInStock() == 0;
            case "All":
                return true;
            default:
                return true;

        }
    }

    public boolean matches(Product product) {
        return this.matchesAvailability(product) && this.matchesCategory(product) && this.matchesName(product);
    }

}
