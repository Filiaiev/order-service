package com.filiaiev.orderservice.service;

import com.filiaiev.orderservice.model.charge.ShortChargeSummary;
import com.filiaiev.orderservice.model.flight.Flight;
import com.filiaiev.orderservice.model.flight.FlightLoad;
import com.filiaiev.orderservice.model.order.CreateOrderRequest;
import com.filiaiev.orderservice.model.order.Order;
import com.filiaiev.orderservice.model.order.OrderStatus;
import com.filiaiev.orderservice.model.order.UpdateOrderStatus;
import com.filiaiev.orderservice.repository.FlightRepository;
import com.filiaiev.orderservice.repository.OrderRepository;
import com.filiaiev.orderservice.repository.RateRepository;
import com.filiaiev.orderservice.repository.entity.charge.CalculateShippingPriceRequestDO;
import com.filiaiev.orderservice.repository.entity.order.OrderDO;
import com.filiaiev.orderservice.repository.entity.order.OrderStatusDO;
import com.filiaiev.orderservice.repository.entity.order.UpdateOrderStatusDO;
import com.filiaiev.orderservice.service.mapper.OrderServiceMapper;
import com.filiaiev.orderservice.service.utils.OrderUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RateRepository rateRepository;
    private final FlightRepository flightRepository;

    private final OrderServiceMapper orderMapper;

    public Order getOrder(Integer orderId) {
        OrderDO order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        return orderMapper.mapOrderDOToOrder(order);
    }

    private List<Order> getOrdersByFlightId(Integer flightId) {
        Instant.now().minus(Duration.ofHours(6));
        return orderMapper.mapOrderDOsToOrders(
                orderRepository.findAllByFlightId(flightId)
        );
    }

    public List<Order> getOrders(Integer userId) {
        List<OrderDO> customerOrders = orderRepository.findAllByCustomerUserId(userId);

        return orderMapper.mapOrderDOsToOrders(customerOrders);
    }

    public void createOrder(CreateOrderRequest createOrder) {
        Order shippingOrder = orderMapper.mapCreateOrderRequestToOrder(createOrder);

        Flight flight = flightRepository.getFlight(shippingOrder.getFlightId());

        validateOrder(shippingOrder, flight);
        enrichOrderWithPrice(shippingOrder, flight);

        OrderDO shippingOrderDO = orderMapper.mapOrderToOrderDO(shippingOrder);

        orderRepository.save(shippingOrderDO);
    }

    public void updateOrderStatus(Integer orderId, UpdateOrderStatus updateRequest) {
        orderRepository.updateOrderStatus(orderId,
                orderMapper.mapUpdateOrderStatusToUpdateOrderStatusDO(updateRequest)
        );
    }

    public void cancelOrder(Integer orderId) {
        orderRepository.updateOrderStatus(orderId, UpdateOrderStatusDO.builder()
                .orderStatus(OrderStatusDO.TERMINATED)
                .build()
        );
    }

    private void enrichOrderWithPrice(Order shippingOrder, Flight flight) {
        CalculateShippingPriceRequestDO request =
                orderMapper.mapOrderToCalculateShippingPriceRequestDO(shippingOrder, flight);
        ShortChargeSummary chargeSummary = rateRepository.calculateShippingPrice(request);

        for (int i = 0; i < shippingOrder.getItems().size(); i++) {
            shippingOrder.getItems().get(i).setShippingPrice(
                    chargeSummary.getItemsPriceList().get(i)
            );
        }

        shippingOrder.setTotalShipmentPrice(chargeSummary.getTotal());
    }

    private boolean validateOrder(Order order, Flight flight) {
        isBookable(flight);
        validateFlightLoad(order, flight);

        return true;
    }

    private boolean isBookable(Flight flight) {
        if (Instant.now().isAfter(flight.getBookableUntil())) {
            throw new IllegalArgumentException("Ability to book expired");
        }

        return true;
    }

    private void validateFlightLoad(Order order, Flight flight) {
        FlightLoad loadAfterOrder = getPotentialFlightLoad(order);
        FlightLoad maxFlightLoad = new FlightLoad(flight.getMaxPayload(), flight.getMaxVolume());

        if (loadAfterOrder.compareTo(maxFlightLoad) > 0) {
            throw new IllegalArgumentException("Not enough capacity");
        }
    }

    private FlightLoad getPotentialFlightLoad(Order order) {
        List<Order> flightOrders = getOrdersByFlightId(order.getFlightId()).stream()
                .filter(o -> OrderStatus.TERMINATED != o.getStatus())
                .collect(Collectors.toList());

        flightOrders.add(order);

        return OrderUtils.getTotalLoad(flightOrders);
    }

}
