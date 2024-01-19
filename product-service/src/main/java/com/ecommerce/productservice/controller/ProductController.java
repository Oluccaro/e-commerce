package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.DTO.ProductRequest;
import com.ecommerce.productservice.DTO.ProductResponse;
import com.ecommerce.productservice.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/product")
// @RequiredArgsConstructor
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
    return productService.createProduct(productRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAllProducts(){
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductResponse getProductById(@PathVariable String id) {
      return productService.getProductById(id);
  }
  
  @GetMapping("/search")
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> searchProductsByName(@RequestParam(name = "name") String name){
    return productService.searchProductsByName(name);
  }


  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ProductResponse updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest){
    return productService.updateProduct(id, productRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable String id){
    productService.deleteProduct(id);
  }
}
