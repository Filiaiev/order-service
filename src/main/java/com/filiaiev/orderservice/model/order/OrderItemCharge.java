package com.filiaiev.orderservice.model.order;

import com.filiaiev.orderservice.model.charge.ChargeType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemCharge {

    private Long id;
    private ChargeType chargeType;
    private BigDecimal charge;
}
