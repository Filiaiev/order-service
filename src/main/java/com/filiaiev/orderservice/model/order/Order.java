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
    private Integer zoneRouteId;
    private Instant createdAt;
    private BigDecimal totalShipmentPrice;
    // deep dive into correct cascade type and saving complex entities (by fk), mappedBy
    private List<OrderItem> items;
    private OrderStatus status;
}
