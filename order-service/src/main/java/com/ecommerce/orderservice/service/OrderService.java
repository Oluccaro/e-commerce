package com.ecommerce.orderservice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.orderservice.DTO.InventoryResponse;
import com.ecommerce.orderservice.DTO.OrderRequest;
import com.ecommerce.orderservice.DTO.OrderResponse;
import com.ecommerce.orderservice.mapper.OrderMapper;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItem;
import com.ecommerce.orderservice.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private WebClient.Builder webClientBuilder;

  public OrderResponse createOrder(OrderRequest orderRequest) throws Exception{
    Order order = orderMapper.orderRequestToOrder(orderRequest);
    List<String> skuCodes = order.getOrderLineItems().stream().map(OrderLineItem::getSkuCode).toList();
    InventoryResponse[] inventoryResponses = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
                                    uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                   .retrieve()
                   .bodyToMono(InventoryResponse[].class)
                   .block();
    boolean allProductIsInStock = true;
    for (InventoryResponse inventoryResponse : inventoryResponses) {
      OrderLineItem orderLineItem = order.getOrderLineItems()
                                         .stream()
                                         .filter(orderItem -> orderItem.getSkuCode().equals(inventoryResponse.getSkuCode()))
                                         .findFirst()
                                         .orElse(null);
      if(orderLineItem == null){
        throw new Exception("An unexpected error occured. Contact support.");
      }
      if(orderLineItem.getQuantity() > inventoryResponse.getQuantity()){
        allProductIsInStock = false;
      }
    }
    if(allProductIsInStock){
      orderRepository.save(order);
    } else {
      throw new IllegalArgumentException("Product is not in stock, try again later");
    }

    OrderResponse orderResponse = orderMapper.orderToOrderResponse(order);
    return orderResponse;
  }
}
