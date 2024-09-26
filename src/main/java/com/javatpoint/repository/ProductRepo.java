package com.javatpoint.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatpoint.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{

}
