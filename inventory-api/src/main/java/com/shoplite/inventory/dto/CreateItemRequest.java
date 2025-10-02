package com.shoplite.inventory.dto;

public class CreateItemRequest {
    private String name;
    private int stock;

    public CreateItemRequest() { }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
