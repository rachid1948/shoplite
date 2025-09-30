package com.shoplite.inventory.service;

import com.shoplite.inventory.domain.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InventoryService {

    private final Map<Long, Product> store = new ConcurrentHashMap<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    public List<Product> listItems() {
        // retourne une copie pour éviter les modifications externes
        return new ArrayList<>(store.values());
    }

    public Product addItem(String name, int stock) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("stock must be >= 0");
        }
        long id = idSeq.getAndIncrement();
        Product p = new Product(id, name, stock);
        store.put(id, p);
        return p;
    }

    /**
     * Réserve du stock pour un produit.
     * @return le produit après décrément, si succès
     * @throws NoSuchElementException si produit introuvable
     * @throws IllegalStateException si stock insuffisant
     */
    public Product reserve(long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        Product p = store.get(productId);
        if (p == null) {
            throw new NoSuchElementException("product not found: " + productId);
        }
        synchronized (p) { // simple verrou sur l'objet pour éviter les réservations concurrentes
            if (p.getStock() < quantity) {
                throw new IllegalStateException("insufficient stock");
            }
            p.setStock(p.getStock() - quantity);
            return p;
        }
    }
}
