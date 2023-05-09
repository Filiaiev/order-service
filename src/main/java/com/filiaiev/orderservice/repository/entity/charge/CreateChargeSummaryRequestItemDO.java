package com.filiaiev.orderservice.repository.entity.charge;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateChargeSummaryRequestItemDO {

    private Integer quantity;
    private Double declaredWeight;
    private DimensionDO dimension;
    private BigDecimal declaredCargoPrice;
}
