package com.filiaiev.orderservice.service.mapper.utils;

import com.filiaiev.orderservice.model.flight.FlightLoad;
import com.filiaiev.orderservice.model.order.Order;
import com.filiaiev.orderservice.model.order.OrderItem;
import com.filiaiev.orderservice.service.utils.OrderUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderUtilsTest {

    @Test
    public void testGetTotalLoad() {
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        orderItem.setActualWeight(500.0);
        orderItem.setHeight(3.0);
        orderItem.setWidth(5.5);
        orderItem.setLength(2.25);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setActualWeight(500.0);
        orderItem2.setHeight(3.0);
        orderItem2.setWidth(5.5);
        orderItem2.setLength(2.25);

        order.setItems(List.of(orderItem, orderItem2));

        FlightLoad totalLoad = OrderUtils.getTotalLoad(List.of(order));

        System.out.println(totalLoad);
    }

    @Test
    public void compare() {
        FlightLoad flightLoad = new FlightLoad(10.0, 50.0);
        FlightLoad flightLoad1 = new FlightLoad(10.0, 40.0);

        System.out.println(flightLoad.compareTo(flightLoad1));
    }
}
