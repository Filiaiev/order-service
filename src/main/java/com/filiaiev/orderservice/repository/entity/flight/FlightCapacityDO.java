package com.filiaiev.orderservice.repository.entity.flight;

import lombok.Data;

@Data
public class FlightCapacityDO {

    private Double maxPayload;
    private Double maxVolume;
}
