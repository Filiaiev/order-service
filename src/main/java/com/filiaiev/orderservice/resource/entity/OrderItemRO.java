package com.filiaiev.orderservice.resource.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRO {

    private Integer quantity;
    private Double declaredWeight;
    private DimensionRO dimension;
    private BigDecimal declaredCargoPrice;
    private BigDecimal shippingPrice;
}
