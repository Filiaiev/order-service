package com.filiaiev.orderservice.model.flight;

import lombok.Data;

import java.time.Instant;

@Data
public class Flight {

    private Integer id;
    private Integer chargeableZoneRouteId;
    private Instant bookableUntil;
    private Double maxPayload;
    private Double maxVolume;
}
