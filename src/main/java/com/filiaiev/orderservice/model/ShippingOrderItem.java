package com.filiaiev.orderservice.model;

import java.math.BigDecimal;

public class ShippingOrderItem {

    private Integer quantity;
    private Double actualWeight;
    private Double height;
    private Double width;
    private Double length;
    private BigDecimal declaredCargoPrice;
    private BigDecimal shipmentPrice;
}
