package com.filiaiev.orderservice.resource.entity;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestRO {

    private Integer flightId;
    private List<CreateOrderRequestItemRO> items;
}
