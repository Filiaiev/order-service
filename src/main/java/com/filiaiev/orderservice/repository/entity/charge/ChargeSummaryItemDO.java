package com.filiaiev.orderservice.repository.entity.charge;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ChargeSummaryItemDO {

    private List<ItemChargeDO> itemBreakdown;
    private BigDecimal total;
}
