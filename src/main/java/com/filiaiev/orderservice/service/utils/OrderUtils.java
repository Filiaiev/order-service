package com.filiaiev.orderservice.service.utils;

import com.filiaiev.orderservice.model.flight.FlightLoad;
import com.filiaiev.orderservice.model.order.Dimension;
import com.filiaiev.orderservice.model.order.Order;

import java.util.List;

public abstract class OrderUtils {

    public static FlightLoad getTotalLoad(List<Order> order) {
        return order.stream().flatMap(o -> o.getItems().stream())
                .map(o -> new FlightLoad(o.getQuantity() * o.getDeclaredWeight(),
                        o.getQuantity() * getVolume(o.getDimension()))
                )
                .reduce(new FlightLoad(0.0, 0.0),
                        (o1, o2) -> {
                            double payload = o1.getPayloadCapacity() + o2.getPayloadCapacity();
                            double volume = o1.getVolumeCapacity() + o2.getVolumeCapacity();
                            return new FlightLoad(payload, volume);
                        });
    }

    public static Double getVolume(Dimension dimension) {
        return dimension.getWidth() * dimension.getHeight() * dimension.getLength();
    }
}
