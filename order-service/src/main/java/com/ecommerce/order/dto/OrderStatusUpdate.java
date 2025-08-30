package com.ecommerce.order.dto;

import com.ecommerce.order.entity.OrderStatus;

public class OrderStatusUpdate {
    private OrderStatus status;

    public OrderStatusUpdate() {}

    public OrderStatusUpdate(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}