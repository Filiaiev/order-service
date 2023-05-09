package com.filiaiev.orderservice.model.charge;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemCharge {

    private ChargeType chargeType;
    private BigDecimal charge;
}
