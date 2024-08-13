package com.alx_tek.order.service;

import com.alx_tek.order.client.InventoryClient;
import com.alx_tek.order.dto.OrderRequest;
import com.alx_tek.order.model.Order;
import com.alx_tek.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isProductInStock) {
            Order order =  Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .skuCode(orderRequest.skuCode())
                    .price(orderRequest.price())
                    .quantity(orderRequest.quantity())
                    .build();
            orderRepository.save(order);
            log.info("Order placed: {}", order.getId());
        } else {
            throw new IllegalArgumentException("Product with skuCode: " + orderRequest.skuCode() + " is not in stock");
        }

    }
}
