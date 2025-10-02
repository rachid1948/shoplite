package com.shoplite.order.domain;

public class Order {
    private Long id;
    private String customer;
    private Long productId;
    private int quantity;
    private OrderStatus status;

    public Order() { }

    public Order(Long id, String customer, Long productId, int quantity, OrderStatus status) {
        this.id = id;
        this.customer = customer;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}

