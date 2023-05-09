package com.filiaiev.orderservice.repository.entity.charge;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemChargeDO {

    private ChargeTypeDO type;
    private BigDecimal charge;
}
