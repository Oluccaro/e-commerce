package com.ecommerce.inventoryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventoryservice.DTO.InventoryResponse;
import com.ecommerce.inventoryservice.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
  
  @Autowired
  private InventoryService inventoryService;

  // http://localhost:8082/api/inventory?skuCode=product1&skuCode=product2
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes){
    return inventoryService.isInStock(skuCodes);
  }
}
