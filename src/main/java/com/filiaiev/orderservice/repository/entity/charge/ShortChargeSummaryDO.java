package com.filiaiev.orderservice.repository.entity.charge;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShortChargeSummaryDO {

    private List<BigDecimal> itemsPriceList;
    private BigDecimal total;
}
