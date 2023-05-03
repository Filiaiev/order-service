package com.filiaiev.orderservice.model.charge;

import lombok.Data;

import java.util.List;

@Data
public class CalculateShippingPriceRequest {

    private Integer zoneRouteId;
    private List<CreateChargeSummaryRequestItem> items;
}
