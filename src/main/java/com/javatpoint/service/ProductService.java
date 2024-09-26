package com.javatpoint.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javatpoint.model.Product;
import com.javatpoint.repository.ProductRepo;

import Helper.Helper;
 

@Service
public class ProductService {
	@Autowired
	private ProductRepo productRepo;
	
	public void save(MultipartFile file) {
		
		try {
		     List<Product> product =Helper.convertToListExcelOfProduct(file.getInputStream());
		     this.productRepo.saveAll(product);
		}catch (IOException e) {
			e.printStackTrace();
			
		}
		
		
	}
	
	public List<Product>getAllProduct(){
		return this.productRepo.findAll();
		 
		
	}

}
