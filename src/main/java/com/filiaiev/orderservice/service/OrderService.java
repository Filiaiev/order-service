package com.filiaiev.orderservice.service;

import com.filiaiev.orderservice.model.charge.ChargeSummary;
import com.filiaiev.orderservice.model.flight.Flight;
import com.filiaiev.orderservice.model.flight.FlightLoad;
import com.filiaiev.orderservice.model.order.CreateOrderRequest;
import com.filiaiev.orderservice.model.order.Order;
import com.filiaiev.orderservice.model.order.OrderStatus;
import com.filiaiev.orderservice.model.order.UpdateOrderStatus;
import com.filiaiev.orderservice.repository.FlightRepository;
import com.filiaiev.orderservice.repository.OrderRepository;
import com.filiaiev.orderservice.repository.RateRepository;
import com.filiaiev.orderservice.repository.entity.charge.CreateChargeSummaryRequestDO;
import com.filiaiev.orderservice.repository.entity.order.OrderDO;
import com.filiaiev.orderservice.repository.entity.order.OrderStatusDO;
import com.filiaiev.orderservice.repository.entity.order.UpdateOrderStatusDO;
import com.filiaiev.orderservice.service.mapper.OrderServiceMapper;
import com.filiaiev.orderservice.service.utils.OrderUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
        return orderMapper.mapOrderDOsToOrders(
                orderRepository.findAllByFlightId(flightId)
        );
    }

    public List<Order> getOrders(Integer userId) {
        List<OrderDO> customerOrders = orderRepository.findAllByCustomerUserId(userId);

        return orderMapper.mapOrderDOsToOrders(customerOrders);
    }

    public void createOrder(Order shippingOrder) {
//        Order shippingOrder = orderMapper.mapCreateOrderRequestToOrder(createOrder);
//
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
        CreateChargeSummaryRequestDO request =
                orderMapper.mapOrderToCalculateShippingPriceRequestDO(shippingOrder, flight);

        ChargeSummary chargeSummary = rateRepository.createChargeSummary(request);

        for (int i = 0; i < shippingOrder.getItems().size(); i++) {
            shippingOrder.getItems().get(i).setItemCharges(
                    orderMapper.mapChargeSummaryToOrderItemCharges(chargeSummary, i)
            );
        }

        shippingOrder.setTotalShipmentPrice(chargeSummary.getTotal());
    }

    private boolean validateOrder(Order order, Flight flight) {
        isBookable(flight);
        hasEnoughCapacity(flight, order);

        return true;
    }

    private boolean isBookable(Flight flight) {
        if (Instant.now().isAfter(flight.getBookableUntil())) {
            throw new IllegalArgumentException("Ability to book expired");
        }

        return true;
    }

    private boolean hasEnoughCapacity(Flight flight, Order order) {
        FlightLoad loadAfterOrder = getPotentialFlightLoad(order);
        FlightLoad maxFlightLoad = new FlightLoad(flight.getMaxPayload(), flight.getMaxVolume());

        if (loadAfterOrder.compareTo(maxFlightLoad) > 0) {
            throw new IllegalArgumentException("Not enough capacity");
        }

        return true;
    }

    private FlightLoad getPotentialFlightLoad(Order order) {
        List<Order> flightOrders = getOrdersByFlightId(order.getFlightId()).stream()
                .filter(o -> OrderStatus.TERMINATED != o.getStatus())
                .collect(Collectors.toList());

        flightOrders.add(order);

        return OrderUtils.getTotalLoad(flightOrders);
    }

}
