package com.ecommerce.orderservice.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.orderservice.DTO.OrderLineItemsDTO;
import com.ecommerce.orderservice.DTO.OrderRequest;
import com.ecommerce.orderservice.DTO.OrderResponse;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItem;

public class OrderMapper {
  
  @Autowired
  private ModelMapper mapper;

  public Order orderRequestToOrder(OrderRequest orderRequest){
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());
    List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDTOList()
                .stream()
                .map(orderLineItemsDTO -> mapper.map(orderLineItemsDTO, OrderLineItem.class))
                .collect(Collectors.toList());
    order.setOrderLineItems(orderLineItems);
    return order;
  }

  public OrderResponse orderToOrderResponse(Order order){
    OrderResponse orderResponse = mapper.map(order, OrderResponse.class);
    List<OrderLineItemsDTO> orderLineItemsDTOList = order.getOrderLineItems().stream()
                                                         .map(orderLineItems -> mapper.map(orderLineItems, OrderLineItemsDTO.class))
                                                         .collect(Collectors.toList());
    orderResponse.setOrderLineItemsDTOList(orderLineItemsDTOList);                                                         
    return orderResponse;
  } 
}
