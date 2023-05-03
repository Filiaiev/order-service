package com.filiaiev.orderservice.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class CreateOrderRequest {

    private Integer customerUserId;
    private Integer flightId;
    private Integer zoneRouteId;
    private Instant createdAt;
    private BigDecimal totalShipmentPrice;
    private List<CreateOrderRequestItem> items;
}
