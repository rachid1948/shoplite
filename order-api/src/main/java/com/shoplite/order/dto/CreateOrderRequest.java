package com.shoplite.order.dto;

public class CreateOrderRequest {
    private String customer;
    private long productId;
    private int quantity;

    public CreateOrderRequest() { }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
