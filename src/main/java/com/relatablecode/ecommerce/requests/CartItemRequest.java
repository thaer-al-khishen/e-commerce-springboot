package com.relatablecode.ecommerce.requests;

public class CartItemRequest {
    private Long productId;
    private int quantity;

    // Getters and setters


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
