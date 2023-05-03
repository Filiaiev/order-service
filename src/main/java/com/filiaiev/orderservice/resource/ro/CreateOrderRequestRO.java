package com.filiaiev.orderservice.resource.ro;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestRO {

    private Integer flightId;
    private Integer zoneRouteId;
    private List<CreateOrderRequestItemRO> items;
}
