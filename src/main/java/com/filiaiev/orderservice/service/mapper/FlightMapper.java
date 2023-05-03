package com.filiaiev.orderservice.service.mapper;

import com.filiaiev.orderservice.model.flight.FlightCapacity;
import com.filiaiev.orderservice.repository.entity.flight.FlightCapacityDO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightCapacity mapFlightCapacityDOToFlightCapacity(FlightCapacityDO flightCapacityDO);
}
