package com.filiaiev.orderservice.repository.entity.flight;

import lombok.Data;

@Data
public class AirportDO {

    private Integer id;
    private String address;
    private String city;
    private String country;
    private String postcode;
    private String iataCode;
}
