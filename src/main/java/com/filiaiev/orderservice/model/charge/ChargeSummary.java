package com.filiaiev.orderservice.model.charge;

import com.filiaiev.orderservice.repository.entity.charge.ChargeSummaryItemDO;
import com.filiaiev.orderservice.repository.entity.charge.ItemChargeDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ChargeSummary {

    private List<ChargeSummaryItem> itemsBreakdown;
    private List<ItemCharge> totalBreakdown;
    private BigDecimal total;
}
