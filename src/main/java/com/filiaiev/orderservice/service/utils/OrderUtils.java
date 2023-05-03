package com.filiaiev.orderservice.service.utils;

import com.filiaiev.orderservice.model.flight.FlightCapacity;
import com.filiaiev.orderservice.model.order.Order;
import com.filiaiev.orderservice.model.order.OrderItem;

public abstract class OrderUtils {

    public static void getTotalLoad(Order order) {
        FlightCapacity capacity = order.getItems().stream()
                .reduce(new FlightCapacity(0.0, 0.0),
                        (o1, o2) -> new FlightCapacity(
                                o1.getActualWeight() + o2.getActualWeight(),
                                getVolume(o1) + getVolume(o2))
                )
    }

    public static Double getVolume(OrderItem orderItem) {
        return orderItem.getWidth() * orderItem.getHeight() * orderItem.getLength();
    }
}
