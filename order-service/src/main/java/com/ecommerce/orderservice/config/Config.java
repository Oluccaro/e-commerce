package com.ecommerce.orderservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.orderservice.mapper.OrderMapper;

@Configuration
public class Config {
  @Bean
  public ModelMapper createModelMapper(){
    return new ModelMapper();
  }
  
  @Bean
  public OrderMapper createOrderMapper(){
    return new OrderMapper();
  }
}
