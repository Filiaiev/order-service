package com.filiaiev.orderservice.resource.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class OrderShortRO {

    private Integer id;
    private Integer flightId;
    private Instant createdAt;
    private BigDecimal totalShipmentPrice;
    private OrderStatusRO status;
}
