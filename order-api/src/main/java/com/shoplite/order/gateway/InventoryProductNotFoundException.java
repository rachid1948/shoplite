package com.shoplite.order.gateway;

public class InventoryProductNotFoundException extends RuntimeException {
    public InventoryProductNotFoundException(String message) {
        super(message);
    }
}
