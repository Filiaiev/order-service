package com.filiaiev.orderservice.resource.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRequestItemRO {

    private Integer quantity;
    private Double actualWeight;
    private Double height;
    private Double width;
    private Double length;
    private BigDecimal declaredCargoPrice;
}
