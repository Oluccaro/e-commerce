package com.ecommerce.productservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.productservice.DTO.ProductRequest;
import com.ecommerce.productservice.DTO.ProductResponse;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ModelMapper mapper;

  public ProductResponse createProduct(ProductRequest productRequest){
    Product product = Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .build();
    if(product != null){
      productRepository.save(product);
      log.info("Product " + product.getId() + " created.");
      return mapper.map(product, ProductResponse.class);
    }
    return null;
  }

  public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream()
                   .map(product -> mapper.map(product, ProductResponse.class))
                   .collect(Collectors.toList());
  }

  public ProductResponse updateProduct(String id, ProductRequest productRequest) {
    Product product = productRepository.findById(id).orElseThrow(
      () -> new RuntimeException(
        String.format("Product with id %s not found", id)
      )
    );
    product.setId(id);
    product.setName(productRequest.getName());
    product.setDescription(productRequest.getDescription());
    product.setPrice(productRequest.getPrice());
    productRepository.save(product);
    return mapper.map(product, ProductResponse.class);
  }

  public void deleteProduct(String id) {
    productRepository.deleteById(id);
  }

  public ProductResponse getProductById(String id) {
    Product product = productRepository.findById(id).orElse(null);
    return mapper.map(product, ProductResponse.class);
  }

  public List<ProductResponse> searchProductsByName(String name) {
    List<Product> products = productRepository.searchProductByName(name).stream().collect(Collectors.toList());
    return products.stream().map(product -> mapper.map(product, ProductResponse.class)).collect(Collectors.toList());
  }
}
