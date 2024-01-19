package com.ecommerce.productservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ecommerce.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
  @Query("{name: {$regex: ?0, $options: 'i'}}")
  List<Product> searchProductByName(String name);
}
