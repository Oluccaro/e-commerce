package com.ecommerce.orderservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orderservice.DTO.OrderRequest;
import com.ecommerce.orderservice.DTO.OrderResponse;
import com.ecommerce.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/order")
public class OrderController {
  
  @Autowired
  private OrderService orderService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CircuitBreaker(name = "inventory", fallbackMethod = "handleCircuitOpen")
  public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) throws Exception {
      return orderService.createOrder(orderRequest);
  }
  
  public String handleCircuitOpen(OrderRequest orderRequest, RuntimeException runtimeException){
    return "Something went wrong. Try again after some time";
  }
}
