package com.breakable.toy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.breakable.toy.exception.ResourceNotFoundException;
import com.breakable.toy.model.Product;
import com.breakable.toy.model.Statistics;

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

		existingProduct.setName(product.getName());
		existingProduct.setCategory(product.getCategory());
		existingProduct.setUnitPrice((float) product.getUnitPrice());
		existingProduct.setQuantityInStock(product.getQuantityInStock());
		existingProduct.setExpirationDate(product.getExpirationDate());
		existingProduct.setUpdateDate(LocalDateTime.now());

		statisticsService.updateStats(existingProduct);
		return existingProduct;
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
				.filter(product -> categories.isEmpty() || categories.contains(product.getCategory()))
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
