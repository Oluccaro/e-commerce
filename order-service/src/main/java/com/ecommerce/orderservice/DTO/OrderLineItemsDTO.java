package com.ecommerce.orderservice.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsDTO {
  private Long id;
  private String skuCode;
  private BigDecimal price;
  private Integer quantity;
}
