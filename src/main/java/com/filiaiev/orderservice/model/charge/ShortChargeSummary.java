package com.filiaiev.orderservice.model.charge;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShortChargeSummary {

    private List<BigDecimal> itemsPriceList;
    private BigDecimal total;
}
