package com.shoplite.order.gateway;

public class InventoryInsufficientStockException extends RuntimeException {
    public InventoryInsufficientStockException(String message) {
        super(message);
    }
}
