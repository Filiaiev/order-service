package com.filiaiev.orderservice.model.flight;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightCapacity {

    private Double payloadCapacity;
    private Double volumeCapacity;
}
