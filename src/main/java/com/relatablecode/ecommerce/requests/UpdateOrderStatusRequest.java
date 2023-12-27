package com.relatablecode.ecommerce.requests;

public class UpdateOrderStatusRequest {
    private String newStatus;

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

}
