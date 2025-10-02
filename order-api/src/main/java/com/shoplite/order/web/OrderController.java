package com.shoplite.order.web;

import com.shoplite.order.domain.Order;
import com.shoplite.order.dto.CreateOrderRequest;
import com.shoplite.order.gateway.InventoryInsufficientStockException;
import com.shoplite.order.gateway.InventoryProductNotFoundException;
import com.shoplite.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    /** GET /api/orders → liste toutes les commandes */
    @GetMapping
    public List<Order> listOrders() {
        return service.listOrders();
    }

    /** POST /api/orders → crée une nouvelle commande */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody CreateOrderRequest req) {
        if (req.getCustomer() == null || req.getCustomer().isBlank()) {
            throw new IllegalArgumentException("customer is required");
        }
        if (req.getProductId() <= 0) {
            throw new IllegalArgumentException("productId must be > 0");
        }
        if (req.getQuantity() <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        return service.create(req.getCustomer(), req.getProductId(), req.getQuantity());
    }

    /** GET /api/orders/{id} → consulter une commande spécifique */
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") Long id) {
        // defensive: si id null ou <= 0 → 404
        if (id == null || id <= 0) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "order not found: " + id
            );
        }

        // on parcourt la liste (évite tout souci de Map.get/autoboxing improbable)
        return service.listOrders().stream()
                .filter(o -> id.equals(o.getId()))
                .findFirst()
                .orElseThrow(() ->
                        new org.springframework.web.server.ResponseStatusException(
                                org.springframework.http.HttpStatus.NOT_FOUND,
                                "order not found: " + id
                        )
                );
    }

    // --- gestion erreurs inventory ---
    @ExceptionHandler(InventoryProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProductNotFound(InventoryProductNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InventoryInsufficientStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleInsufficientStock(InventoryInsufficientStockException ex) {
        return ex.getMessage();
    }




}
