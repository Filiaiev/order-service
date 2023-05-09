package com.filiaiev.orderservice.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItem {

    private Integer id;
    private Integer quantity;
    private Double declaredWeight;
    private Dimension dimension;
    private BigDecimal declaredCargoPrice;
    private List<OrderItemCharge> itemCharges;
}
