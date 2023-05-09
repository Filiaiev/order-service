package com.filiaiev.orderservice.repository.entity.charge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ChargeTypeDO {

    WEIGHT("Weight charge"),
    SERVICE("Service charge"),
    SURCHARGE_FUEL("Surcharge - fuel"),
    SURCHARGE_SECURITY("Surcharge - security");

    private static final Map<String, ChargeTypeDO> identityMap = Arrays.stream(ChargeTypeDO.values())
            .collect(Collectors.toMap(type -> type.id, Function.identity()));

    private final String id;

    @JsonCreator
    public static ChargeTypeDO forValue(String id) {
        return identityMap.get(id);
    }
}
