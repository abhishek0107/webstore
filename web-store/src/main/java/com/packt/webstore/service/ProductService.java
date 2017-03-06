package com.packt.webstore.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.packt.webstore.domain.Product;

public interface ProductService {
	Product getProductByProductId(String productID);
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String Category);
	Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
	void addProduct(Product product);
}
