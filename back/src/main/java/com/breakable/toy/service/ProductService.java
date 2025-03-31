package com.breakable.toy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.breakable.toy.exception.ResourceNotFoundException;
import com.breakable.toy.model.Product;

@Service
public class ProductService {

	private final List<Product> products = new ArrayList<>();
	private final StatisticsService statisticsService;

	public ProductService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	public List<Product> getAllProducts() {
		return new ArrayList<>(products);
	}

	public Set<String> getCategories() {
		return products.stream()
				.map(Product::getCategory)
				.collect(Collectors.toSet());
	}

	public boolean existsById(String id) {
		return products.stream().anyMatch(p -> p.getId().equals(id));
	}

	public Product createProduct(Product product) {
		if (!product.fieldsAreValid()) {
			throw new IllegalArgumentException("Invalid product data");
		}
		
		product.setId();
		product.setCreationDate(LocalDateTime.now());
		product.setUpdateDate(LocalDateTime.now());
		
		products.add(product);
		statisticsService.updateStats(product);
		return product;
	}

	public Product updateProduct(String id, Product product) {
		Product existingProduct = products.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		Product oldProductState = copyProduct(existingProduct);
		String oldCategory = oldProductState.getCategory();
		
		existingProduct.setName(product.getName());
		existingProduct.setCategory(product.getCategory());
		existingProduct.setUnitPrice((float) product.getUnitPrice());
		existingProduct.setQuantityInStock(product.getQuantityInStock());
		existingProduct.setExpirationDate(product.getExpirationDate());
		existingProduct.setUpdateDate(LocalDateTime.now());

		if (!oldCategory.equals(existingProduct.getCategory())) {
			statisticsService.updateProductCategory(oldCategory, existingProduct);
		} else {
			statisticsService.updateProductStats(oldProductState, existingProduct);
		}
		
		return existingProduct;
	}
	
	/**
	 * Sets a product as out of stock (quantity = 0) and updates statistics accordingly
	 * 
	 * @param id The ID of the product to set out of stock
	 * @return The updated product
	 * @throws ResourceNotFoundException If the product is not found
	 */
	public Product setProductOutOfStock(String id) {
		Product product = getProductById(id);
		
		// Only update if product is not already out of stock
		if (product.getQuantityInStock() > 0) {
			Product oldState = copyProduct(product);
			
			// Update the quantity to 0
			product.setQuantityInStock(0);
			product.setUpdateDate(LocalDateTime.now());
			
			// Update statistics
			statisticsService.updateProductStats(oldState, product);
		}
		
		return product;
	}
	
	/**
	 * Updates a product's stock level and updates statistics accordingly
	 * 
	 * @param id The ID of the product to update
	 * @param newQuantity The new stock quantity (must be > 0)
	 * @return The updated product
	 * @throws ResourceNotFoundException If the product is not found
	 * @throws IllegalArgumentException If the quantity is invalid
	 */
	public Product setProductStockLevel(String id, int newQuantity) {
		if (newQuantity <= 0) {
			throw new IllegalArgumentException("Stock quantity must be greater than zero");
		}
		
		Product product = getProductById(id);
		
		// Only update if the quantity has actually changed
		if (product.getQuantityInStock() != newQuantity) {
			Product oldState = copyProduct(product);
			
			// Update the quantity
			product.setQuantityInStock(newQuantity);
			product.setUpdateDate(LocalDateTime.now());
			
			// Update statistics
			statisticsService.updateProductStats(oldState, product);
		}
		
		return product;
	}
	
	private Product copyProduct(Product source) {
		Product copy = new Product();
		copy.setId();
		copy.setName(source.getName());
		copy.setCategory(source.getCategory());
		copy.setUnitPrice((float) source.getUnitPrice());
		copy.setQuantityInStock(source.getQuantityInStock());
		copy.setExpirationDate(source.getExpirationDate());
		copy.setCreationDate(source.getCreationDate());
		copy.setUpdateDate(source.getUpdateDate());
		return copy;
	}

	public void deleteProduct(String id) {
		Product product = products.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		
		statisticsService.removeProduct(product);
		products.remove(product);
	}

	public List<Product> getFilteredProducts(String name, List<String> categories, String availability) {
		return products.stream()
				.filter(product -> name == null || product.getName().toLowerCase().contains(name.toLowerCase()))
				.filter(product -> categories == null || categories.isEmpty() || categories.contains(product.getCategory()))
				.filter(product -> {
					if (availability == null) return true;
					return switch (availability.toLowerCase()) {
						case "in stock" -> product.getQuantityInStock() > 0;
						case "out of stock" -> product.getQuantityInStock() == 0;
						default -> true;
					};
				})
				.collect(Collectors.toList());
	}

	public Product getProductById(String id) {
		return products.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}
}
