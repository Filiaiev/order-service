package com.filiaiev.orderservice.model.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateOrderRequestItem {

    private Integer shippingOrderId;
    private Integer quantity;
    private Double actualWeight;
    private Dimension dimension;
    private BigDecimal declaredCargoPrice;
}
