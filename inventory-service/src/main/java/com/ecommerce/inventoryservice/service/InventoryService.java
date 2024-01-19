package com.ecommerce.inventoryservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.inventoryservice.DTO.InventoryResponse;
import com.ecommerce.inventoryservice.repository.InventoryRepository;

@Service
public class InventoryService {
  
  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<InventoryResponse> isInStock(List<String> skuCodes){
    return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                                             .map(inventory -> modelMapper.map(inventory, InventoryResponse.class))
                                             .collect(Collectors.toList());

  }
}
