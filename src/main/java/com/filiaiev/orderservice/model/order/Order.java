package com.filiaiev.orderservice.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class Order {

    private Integer id;
    private Integer customerUserId;
    private Integer flightId;
    private Instant createdAt;
    private BigDecimal totalShipmentPrice;
    private List<OrderItem> items;
    private OrderStatus status;
}
