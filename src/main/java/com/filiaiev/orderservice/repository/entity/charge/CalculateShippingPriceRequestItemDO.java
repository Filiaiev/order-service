package com.filiaiev.orderservice.repository.entity.charge;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculateShippingPriceRequestItemDO {

    private Integer quantity;
    private Double actualWeight;
    private Double height;
    private Double width;
    private Double length;
    private BigDecimal declaredCargoPrice;
}
