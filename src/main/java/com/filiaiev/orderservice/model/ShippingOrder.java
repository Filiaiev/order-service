package com.filiaiev.orderservice.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class ShippingOrder {

    private Integer customerUserId;
    private Integer flightId;
    private OffsetDateTime createdAt;
    private BigDecimal totalShippingPrice;
    private List<ShippingOrderItem> shippingOrderItems;
    private ShippingStatus shippingStatus;
}
