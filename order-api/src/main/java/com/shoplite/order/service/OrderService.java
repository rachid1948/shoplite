package com.shoplite.order.service;

import com.shoplite.order.domain.Order;
import com.shoplite.order.domain.OrderStatus;
import com.shoplite.order.gateway.InventoryGateway;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final Map<Long, Order> store = new ConcurrentHashMap<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    private final InventoryGateway inventoryGateway;

    public OrderService(InventoryGateway inventoryGateway) {
        this.inventoryGateway = inventoryGateway;
    }

    public List<Order> listOrders() {
        return new ArrayList<>(store.values());
    }

    public Order create(String customer, long productId, int quantity) {
        if (customer == null || customer.isBlank()) {
            throw new IllegalArgumentException("customer is required");
        }
        if (productId <= 0) {
            throw new IllegalArgumentException("productId must be > 0");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }

        // ✅ étape nouvelle : appeler inventory-api
        inventoryGateway.reserveStock(productId, quantity);

        long id = idSeq.getAndIncrement();
        Order order = new Order(id, customer, productId, quantity, OrderStatus.NEW);
        store.put(id, order);
        return order;
    }

    public Optional<Order> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }
}
