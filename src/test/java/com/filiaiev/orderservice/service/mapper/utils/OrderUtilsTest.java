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

    }

    @Test
    public void compare() {
        FlightLoad flightLoad = new FlightLoad(10.0, 50.0);
        FlightLoad flightLoad1 = new FlightLoad(10.0, 40.0);

        System.out.println(flightLoad.compareTo(flightLoad1));
    }
}
