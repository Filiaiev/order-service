package com.filiaiev.orderservice.repository;

import com.filiaiev.orderservice.model.flight.Flight;
import com.filiaiev.orderservice.repository.entity.flight.FlightDO;
import com.filiaiev.orderservice.service.mapper.FlightMapper;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class FlightRepository extends ApiRepository {

    private static final String FLIGHTS_PATH = "/flights";

    private final FlightMapper flightMapper;

    public FlightRepository(@Value("${flight-service.base-url}") String baseUrl,
                            @Value("${logging.level.webclient}") LogLevel logLevel,
                            FlightMapper flightMapper) {
        super(baseUrl, logLevel);
        this.flightMapper = flightMapper;
    }

    public Flight getFlight(Integer flightId) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(FLIGHTS_PATH + "/{flightId}")
                        .build(flightId)
                )
                .retrieve()
                .bodyToMono(FlightDO.class)
                .map(flightMapper::mapFlightDOToFlight)
                .block();
    }
}
