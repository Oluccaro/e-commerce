package com.ecommerce.orderservice.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_order_line_items")
public class OrderLineItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String skuCode;
  private BigDecimal price;
  private Integer quantity;
}
