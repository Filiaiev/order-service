package com.filiaiev.orderservice.model.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {

    private Integer id;
    private Integer quantity;
    private Double actualWeight;
    private Double height;
    private Double width;
    private Double length;
    private BigDecimal declaredCargoPrice;
    private BigDecimal shippingPrice;
}
