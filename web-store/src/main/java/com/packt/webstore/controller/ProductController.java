package com.packt.webstore.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;
import com.packt.webstore.validator.ProductValidator;
import com.packt.webstore.validator.UnitsInStockValidator;


@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	ProductValidator productValidator;
	//bean validation
//	private UnitsInStockValidator unitsInStockValidator;
	
	@RequestMapping
	public String list(Model model){
		model.addAttribute("products",productService.getAllProducts());
		return "products";
	}
	
	@RequestMapping("/{category}")
	public String getProductsByCategeory(Model model, @PathVariable("category")
	 String productCategory){
		List<Product> products=productService.getProductsByCategory(productCategory);
		if(products==null || products.isEmpty()){
			throw new NoProductsFoundUnderCategoryException();
		}
		model.addAttribute("products",products);
		return "products";
	}
	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(Model model, @MatrixVariable(
			pathVar="ByCriteria") Map<String, List<String>> filterParams){
		model.addAttribute("products",productService.getProductsByFilter(filterParams));
		return "products";
	}
	@RequestMapping("/product")
	public String getProductById(Model model,@RequestParam("id") String productid){
		model.addAttribute("product", productService.getProductByProductId(productid));
		return "product";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String getAddnewProductForm(Model model){
		Product newProduct= new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String processAddnewProductForm(@ModelAttribute("newProduct") @Valid Product newProduct, BindingResult result, HttpServletRequest request){
		if(result.hasErrors()){
			return "addProduct";
		}
		String[] suppressedFields=result.getSuppressedFields();
		if(suppressedFields.length>0){
			throw new RuntimeException(" Attempting to bind disallowed fields:"+StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}
		MultipartFile productImage=newProduct.getProductImage();
		String rootDirectory=request.getSession().getServletContext().getRealPath("/");
		if(productImage!=null&&!productImage.isEmpty()){
			
			try{
				productImage.transferTo(new File(rootDirectory+"resources\\images\\"+newProduct.getProductId()+".png"));
			}catch(Exception e){
				
			}
		}
		productService.addProduct(newProduct);
		return "redirect:/products";
	}
	
	@RequestMapping("/invalidPromoCode")
	public String invalidPromoCode(){
		return "invalidPromoCode";
	}
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder){
		binder.setAllowedFields("productId","name","unitPrice","description","manufacturer","category",
				"unitsInStock","productImage","language");
	//	binder.setDisallowedFields("unitsInOrder","discontinued");
		//binder.setValidator(unitsInStockValidator);
		binder.setValidator(productValidator);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req,ProductNotFoundException exception){
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		mav.setViewName("productNotFound");;
		return mav;
		
	}
	
}
