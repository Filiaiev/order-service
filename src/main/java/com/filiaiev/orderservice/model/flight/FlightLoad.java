package com.filiaiev.orderservice.model.flight;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightLoad implements Comparable<FlightLoad> {

    private Double payloadCapacity;
    private Double volumeCapacity;

    @Override
    public int compareTo(FlightLoad o) {
        if(payloadCapacity > o.getPayloadCapacity() || volumeCapacity > o.getVolumeCapacity()) {
            return 1;
        }else if(payloadCapacity.equals(o.getPayloadCapacity()) && volumeCapacity.equals(o.getPayloadCapacity())) {
            return 0;
        }else {
            return -1;
        }
    }
}
