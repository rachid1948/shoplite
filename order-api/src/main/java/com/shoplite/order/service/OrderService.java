package com.shoplite.order.service;

import com.shoplite.order.domain.Order;
import com.shoplite.order.domain.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final Map<Long, Order> store = new ConcurrentHashMap<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    /** Retourne toutes les commandes (MVP). */
    public List<Order> listOrders() {
        return new ArrayList<>(store.values());
    }

    /**
     * Crée une commande en mémoire.
     * (MVP : pas encore d'appel à inventory ; on le fera après)
     */
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

        long id = idSeq.getAndIncrement();
        Order order = new Order(id, customer, productId, quantity, OrderStatus.NEW);
        store.put(id, order);
        return order;
    }

    /** Récupère une commande par id (servira pour GET /orders/{id} plus tard). */
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }
}
