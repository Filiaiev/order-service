package com.filiaiev.orderservice.service;

import com.filiaiev.orderservice.exception.OrderException;
import com.filiaiev.orderservice.model.UserRole;
import com.filiaiev.orderservice.model.charge.ChargeSummary;
import com.filiaiev.orderservice.model.flight.Flight;
import com.filiaiev.orderservice.model.flight.FlightLoad;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    public static final String FLIGHT_IS_NOT_AVAILABLE_TO_BOOK = "Flight is not available to book";

    private final OrderRepository orderRepository;
    private final RateRepository rateRepository;
    private final FlightRepository flightRepository;

    private final OrderServiceMapper orderMapper;

    public Order getOrder(Integer orderId, Authentication authentication) {
        OrderDO order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found", HttpStatus.NOT_FOUND));

        Order orderEntity = orderMapper.mapOrderDOToOrder(order);

        validateUserOrder(authentication, orderEntity);

        return orderEntity;
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

    public void cancelOrder(Integer orderId, Authentication authentication) {
        OrderDO orderDO = orderRepository.getReferenceById(orderId);
        Order order = orderMapper.mapOrderDOToOrder(orderDO);

        validateUserOrder(authentication, order);

        order.setStatus(OrderStatus.TERMINATED);

        OrderDO updatedOrder = orderMapper.mapOrderToOrderDO(order);

        orderRepository.save(updatedOrder);
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
            throw new OrderException(FLIGHT_IS_NOT_AVAILABLE_TO_BOOK + ": booking time window expired");
        }

        return true;
    }

    private boolean hasEnoughCapacity(Flight flight, Order order) {
        FlightLoad loadAfterOrder = getPotentialFlightLoad(order);
        FlightLoad maxFlightLoad = new FlightLoad(flight.getMaxPayload(), flight.getMaxVolume());

        if (loadAfterOrder.compareTo(maxFlightLoad) > 0) {
            throw new OrderException(FLIGHT_IS_NOT_AVAILABLE_TO_BOOK + ": not enough capacity");
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

    private boolean validateUserOrder(Authentication authentication, Order order) {
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (authorities.contains(UserRole.ROLE_CUSTOMER.name()) &&
                authentication.getPrincipal() != order.getCustomerUserId()) {

            throw new OrderException("Access denied", HttpStatus.FORBIDDEN);
        }

        return true;
    }

}
