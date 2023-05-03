package com.filiaiev.orderservice.repository.entity.charge;

import lombok.Data;

import java.util.List;

@Data
public class CalculateShippingPriceRequestDO {

    private Integer zoneRouteId;
    private List<CalculateShippingPriceRequestItemDO> items;
}
