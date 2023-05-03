package com.filiaiev.orderservice.resource.ro;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrderDetailedRO {

    private Integer id;
    private Integer flightId;
    private Instant createdAt;
    private BigDecimal totalShipmentPrice;
    private List<OrderItemRO> items;
    private OrderStatusRO status;
}
