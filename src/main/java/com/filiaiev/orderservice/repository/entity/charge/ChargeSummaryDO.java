package com.filiaiev.orderservice.repository.entity.charge;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ChargeSummaryDO {

    private List<ChargeSummaryItemDO> itemsBreakdown;
    private List<ItemChargeDO> totalBreakdown;
    private BigDecimal total;
}
