package com.packt.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	ProductRepository productRepository;
	@Override
	public void processOrder(String productId, int quantity) {
		Product productById=productRepository.getProductByProductId(productId);

		if(productById.getUnitsInStock() < quantity){
			throw new IllegalArgumentException(" Out Of Stock. "
					+ "Available Units in Stock " + productById.getUnitsInStock());
		}
		productById.setUnitsInStock(productById.getUnitsInStock()-quantity);
	}

}
