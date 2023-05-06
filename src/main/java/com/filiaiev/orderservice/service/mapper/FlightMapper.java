package com.filiaiev.orderservice.service.mapper;

import com.filiaiev.orderservice.model.flight.Flight;
import com.filiaiev.orderservice.repository.entity.flight.FlightDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(target = "chargeableZoneRouteId", source = "flightRoute.chargeableZoneRouteId")
    Flight mapFlightDOToFlight(FlightDO flightDO);
}
