package com.breakable.toy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.breakable.toy.model.Product;
import com.breakable.toy.model.Statistics;

@Service
public class ProductService {

	private Set<String> categories = new HashSet<String>();
	private ArrayList<Product> products = new ArrayList<Product>();
	private Set<String> ids = new HashSet<String>();
	private HashMap<String, Statistics> statistics = new HashMap<>();

	public ProductService() {
		this.createProduct(new Product("Bananas", "Food", 10.2,
				Optional.of(LocalDateTime.of(2025, 1, 31, 12, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 123));
		this.createProduct(new Product("Apples", "Food", 15.5,
				Optional.of(LocalDateTime.of(2025, 2, 15, 10, 30, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 100));
		this.createProduct(new Product("Oranges", "Food", 12.8,
				Optional.of(LocalDateTime.of(2025, 3, 10, 9, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 85));
		this.createProduct(
				new Product("Milk", "Dairy", 8.0, Optional.of(LocalDateTime.of(2025, 2, 1, 8, 0, 0, 0)),
						LocalDateTime.now(), LocalDateTime.now(), 200));
		this.createProduct(new Product("Cheese", "Dairy", 25.5,
				Optional.of(LocalDateTime.of(2025, 4, 5, 12, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 50));
		this.createProduct(new Product("Bread", "Bakery", 5.2,
				Optional.of(LocalDateTime.of(2025, 1, 15, 15, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 150));
		this.createProduct(new Product("Eggs", "Dairy", 6.5,
				Optional.of(LocalDateTime.of(2025, 2, 10, 7, 30, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 300));
		this.createProduct(new Product("Chicken", "Meat", 32.5,
				Optional.of(LocalDateTime.of(2025, 2, 20, 10, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 45));
		this.createProduct(new Product("Beef", "Meat", 50.0,
				Optional.of(LocalDateTime.of(2025, 5, 12, 12, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 30));
		this.createProduct(new Product("Pork", "Meat", 45.0,
				Optional.of(LocalDateTime.of(2025, 3, 1, 18, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 40));
		this.createProduct(new Product("Lettuce", "Vegetables", 3.5,
				Optional.of(LocalDateTime.of(2025, 1, 28, 8, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 90));
		this.createProduct(new Product("Tomatoes", "Vegetables", 7.0,
				Optional.of(LocalDateTime.of(2025, 2, 14, 13, 30, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 80));
		this.createProduct(new Product("Cucumbers", "Vegetables", 4.2,
				Optional.of(LocalDateTime.of(2025, 2, 25, 14, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 120));
		this.createProduct(new Product("Carrots", "Vegetables", 5.5,
				Optional.of(LocalDateTime.of(2025, 3, 10, 9, 30, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 110));
		this.createProduct(new Product("Potatoes", "Vegetables", 2.8,
				Optional.of(LocalDateTime.of(2025, 6, 5, 12, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 300));
		this.createProduct(new Product("Rice", "Grains", 10.0,
				Optional.of(LocalDateTime.of(2025, 12, 31, 17, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 500));
		this.createProduct(new Product("Pasta", "Grains", 12.0,
				Optional.of(LocalDateTime.of(2025, 11, 1, 18, 30, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 400));
		this.createProduct(new Product("Flour", "Grains", 6.0,
				Optional.of(LocalDateTime.of(2025, 7, 30, 8, 30, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 200));
		this.createProduct(new Product("Oil", "Cooking", 20.0,
				Optional.of(LocalDateTime.of(2025, 3, 15, 9, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 75));
		this.createProduct(new Product("Sugar", "Baking", 5.5,
				Optional.of(LocalDateTime.of(2025, 4, 10, 14, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 220));
		this.createProduct(new Product("Salt", "Spices", 2.0,
				Optional.of(LocalDateTime.of(2025, 8, 25, 17, 30, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 500));
		this.createProduct(new Product("Pepper", "Spices", 15.0,
				Optional.of(LocalDateTime.of(2025, 9, 10, 13, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 100));
		this.createProduct(new Product("Butter", "Dairy", 18.0,
				Optional.of(LocalDateTime.of(2025, 5, 15, 8, 30, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 65));
		this.createProduct(new Product("Yogurt", "Dairy", 9.0,
				Optional.of(LocalDateTime.of(2025, 6, 12, 11, 0, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 120));
		this.createProduct(new Product("Water", "Beverages", 1.5,
				Optional.of(LocalDateTime.of(2025, 12, 5, 14, 30, 0, 0)), LocalDateTime.now(),
				LocalDateTime.now(), 600));

		for (Product p : this.products) {
			this.categories.add(p.getCategory());
			this.ids.add(p.getId());
		}

	}

	public Iterable<Product> getAllProducts() {
		return this.products;
	}

	public Iterable<String> getCategories() {
		return this.categories;
	}

	public boolean containsProduct(String id) {
		return this.ids.contains(id);
	}

	public Product createProduct(Product product) {
		this.categories.add(product.getCategory());
		product.setId();
		this.ids.add(product.getId());
		product.setCreationDate(LocalDateTime.now());
		product.setUpdateDate(LocalDateTime.now());
		this.products.add(product);

		// Updating stats
		updateStats(product);

		return product;
	}

	public void updateStats(Product product) {
		if (this.statistics.containsKey(product.getCategory())) {
			statistics.get(product.getCategory()).addProduct(product);
		} else {
			statistics.put(product.getCategory(), new Statistics(product));
		}
	}

	public Product updateProduct(Product product) {
		int productIndex = -1;
		System.out.println(product);
		for (Product p : this.products) {
			if (p.getId().equals(product.getId())) {
				// If the category remains the same:
				if (statistics.containsKey(product.getCategory())) {
					// Removes the past product from the statistics.
					statistics.get(product.getCategory()).removeProduct(p);
					productIndex = this.products.indexOf(p);
					// Adds the updated product to the statistics.
					updateStats(product);
					this.products.set(productIndex, product);
				} else {
					// Removes the contribution of the past product to
					// the past category
					statistics.get(p.getCategory()).removeProduct(p);
					productIndex = this.products.indexOf(p);
					// Adds the updated product to the statistics.
					updateStats(product);
					this.products.set(productIndex, product);
				}
				this.categories.add(product.getCategory());
			}
		}

		return product;
	}

	public HashMap<String, Statistics> getStatistics() {
		return this.statistics;
	}

	public List<Product> getFilteredElements(String name, ArrayList<String> categories, String availiability) {
		return this.products.stream()
				.filter(product -> (name == null || product.getName().contains(name)))
				.filter(product -> (categories.isEmpty() ||
						categories.contains(product.getCategory())))
				.filter(product -> (availiability == null
						|| this.matchesAvailability(availiability, product)))
				.collect(Collectors.toList());
	}

	private boolean matchesAvailability(String availability, Product product) {
		switch (availability) {
			case "In Stock":
				return product.getQuantityInStock() > 0;
			case "Out Of Stock":
				return product.getQuantityInStock() == 0;
			default:
				return true;
		}
	}

	public void deleteProduct(String id) {
		int productIndex = -1;

		for (Product p : this.products) {
			if (p.getId().equals(id)) {
				productIndex = this.products.indexOf(p);
				this.products.remove(productIndex);
				return;
			}
		}
	}
}
