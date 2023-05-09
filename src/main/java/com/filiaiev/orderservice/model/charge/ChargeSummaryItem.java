package com.filiaiev.orderservice.model.charge;

import com.filiaiev.orderservice.repository.entity.charge.ItemChargeDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ChargeSummaryItem {

    private List<ItemCharge> itemBreakdown;
    private BigDecimal total;
}
