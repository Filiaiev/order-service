package com.filiaiev.orderservice.repository.entity.flight;

import lombok.Data;

import java.time.Instant;

@Data
public class FlightDO {
    
    private Integer id;
    private AircraftDO aircraft;
    private FlightRouteDO flightRoute;
    private Instant originDepartureTime;
    private Instant destinationArrivalTime;
    private Instant bookableUntil;
    private Double maxPayload;
    private Double maxVolume;
    private String trackUrl;
}
