package com.filiaiev.orderservice.model.charge;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateChargeSummaryRequestItem {

    private BigDecimal quantity;
    // Call declaredWeight
    private Double actualWeight;
    // Call shippingWeight
//    private Double finalShippingWeight;
    private Double height;
    private Double width;
    private Double length;
    private BigDecimal declaredCargoPrice;
}
