package com.shoplite.inventory.web;

import com.shoplite.inventory.domain.Product;
import com.shoplite.inventory.dto.CreateItemRequest;
import com.shoplite.inventory.dto.ReserveRequest;
import com.shoplite.inventory.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("/items")
    public List<Product> listItems() {
        return service.listItems();
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addItem(@RequestBody CreateItemRequest req) {
        if (req.getName() == null || req.getName().isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (req.getStock() < 0) {
            throw new IllegalArgumentException("stock must be >= 0");
        }
        return service.addItem(req.getName(), req.getStock());
    }

    @PostMapping("/reservations")
    public Product reserve(@RequestBody ReserveRequest req) {
        if (req.getQuantity() <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        try {
            return service.reserve(req.getProductId(), req.getQuantity());
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (IllegalStateException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class ResourceNotFoundException extends RuntimeException {
        ResourceNotFoundException(String msg) { super(msg); }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    static class ConflictException extends RuntimeException {
        ConflictException(String msg) { super(msg); }
    }
}
