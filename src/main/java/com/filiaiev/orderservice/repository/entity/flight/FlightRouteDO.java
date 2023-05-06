package com.filiaiev.orderservice.repository.entity.flight;

import lombok.Data;

@Data
public class FlightRouteDO {

    private AirportDO originAirport;
    private AirportDO destinationAirport;
    private Integer chargeableZoneRouteId;
}
