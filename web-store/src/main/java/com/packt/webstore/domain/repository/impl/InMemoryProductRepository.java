package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.ProductNotFoundException;

@Repository
public class InMemoryProductRepository implements ProductRepository {
	private List<Product> listOfProducts = new ArrayList<Product>();

	public InMemoryProductRepository() {
		Product iphone=new Product("P1234", "iphone 5s", new BigDecimal(500));
		iphone.setDescription("Apple iPhone 5s smartphone with 4.00-inch 640 X 1136 Display"
				+ " and 8 megapixel rear camera");
		iphone.setCategory("Smart Phone");
		iphone.setManufacturer("Apple");
		iphone.setUnitsInStock(1000);
		
		Product laptop_dell=new Product("P1235", "Dell Inspiron", 
				new BigDecimal(700));
		laptop_dell.setDescription("Dell Inspiron 14-inch laptop (Black) with 3rd Generation "
				+ "intel Core processors");
		laptop_dell.setCategory("Laptop");
		laptop_dell.setManufacturer("Dell");
		laptop_dell.setUnitsInStock(1000);
		
		Product tablet_nexus=new Product("P1236", "Nexus 7", 
				new BigDecimal(300));
		tablet_nexus.setDescription("Google nexus 7 is the lightest 7 inch"
				+ " tablet with a quad-core Qualcomm Snapdragon S4 Processor ");
		tablet_nexus.setCategory("Tablet");
		tablet_nexus.setManufacturer("Google");
		tablet_nexus.setUnitsInStock(1000);
		listOfProducts.add(iphone);
		listOfProducts.add(laptop_dell);
		listOfProducts.add(tablet_nexus);
	}

	@Override
	public List<Product> getAllProducts() {
		return listOfProducts;
	}

	@Override
	public Product getProductByProductId(String productId) {
		Product productById=null;
		 for(Product product:listOfProducts){
			 
			 if(product!=null && product.getProductId()!=null
					 && product.getProductId().equals(productId)){
				 productById=product;
				 break;
				 
			 }
			 
		 }
		 if(productById==null){
			 //throw new IllegalArgumentException(" No Product found with the product Id : "+ productId);
			 throw new ProductNotFoundException(" No Product found with the product Id : "+ productId);
		 }
		
		return productById;
	}
	
	public List<Product> getProductsByCategory(String category){
		List<Product> productsByCategory= new ArrayList<Product>();
		for(Product product : listOfProducts){
			if(category.equalsIgnoreCase(product.getCategory())){
				productsByCategory.add(product);
			}
		}
		return productsByCategory;
	}
	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams){
		Set<Product> productsByBrand=new HashSet<Product>();
		Set<Product> productsByCategory=new HashSet<Product>();
		Set<String> criterias=filterParams.keySet();
		if(criterias.contains("brand")){
			for(String brandName:filterParams.get("brand")){
				for(Product product:listOfProducts){
					if(brandName.equalsIgnoreCase(product.getManufacturer())){
						productsByBrand.add(product);
					}
				}
			}
		}
		if(criterias.contains("category")){
			for(String category:filterParams.get("category")){
				for(Product product:listOfProducts){
					if(category.equalsIgnoreCase(product.getCategory())){
						productsByCategory.addAll(this.getProductsByCategory(category));
					}
				}
			}
		}
		productsByCategory.retainAll(productsByBrand);
		return productsByCategory;
		
		
	}
	public void addProduct(Product product){
		listOfProducts.add(product);
		
	}

}
