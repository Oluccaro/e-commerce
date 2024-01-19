package com.ecommerce.inventoryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.inventoryservice.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
  List<Inventory> findBySkuCodeIn(List<String> skuCodes);
}
