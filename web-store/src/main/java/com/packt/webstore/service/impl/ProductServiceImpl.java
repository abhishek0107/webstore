package com.packt.webstore.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;
	public List<Product> getAllProducts(){
		return productRepository.getAllProducts();
	}
	@Override
	public List<Product> getProductsByCategory(String Category) {

		return productRepository.getProductsByCategory(Category);
	}
	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams){
		return productRepository.getProductsByFilter(filterParams); 
		
	}
	public Product getProductByProductId(String productID){
		return productRepository.getProductByProductId(productID);
		
	}
	public void addProduct(Product product){
		
		 productRepository.addProduct(product);
	}

}
