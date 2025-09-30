package com.shoplite.inventory.dto;

public class ReserveRequest {
    private long productId;
    private int quantity;

    public ReserveRequest() { }

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
