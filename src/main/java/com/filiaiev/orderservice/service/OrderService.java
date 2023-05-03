package com.filiaiev.orderservice.service;

import com.filiaiev.orderservice.model.charge.ShortChargeSummary;
import com.filiaiev.orderservice.model.flight.FlightCapacity;
import com.filiaiev.orderservice.model.order.CreateOrderRequest;
import com.filiaiev.orderservice.model.order.Order;
import com.filiaiev.orderservice.model.order.UpdateOrderStatus;
import com.filiaiev.orderservice.repository.FlightRepository;
import com.filiaiev.orderservice.repository.OrderRepository;
import com.filiaiev.orderservice.repository.RateRepository;
import com.filiaiev.orderservice.repository.entity.charge.CalculateShippingPriceRequestDO;
import com.filiaiev.orderservice.repository.entity.order.OrderDO;
import com.filiaiev.orderservice.repository.entity.order.OrderStatusDO;
import com.filiaiev.orderservice.repository.entity.order.UpdateOrderStatusDO;
import com.filiaiev.orderservice.service.mapper.OrderServiceMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Order> getOrders(Integer userId) {
        List<OrderDO> customerOrders = orderRepository.findAllByCustomerUserId(userId);

        return orderMapper.mapOrderDOsToOrders(customerOrders);
    }

    public void createOrder(CreateOrderRequest request) {
        Order shippingOrder = orderMapper.mapCreateOrderRequestToOrder(request);

        enrichOrderWithPrice(shippingOrder);

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

    private void enrichOrderWithPrice(Order shippingOrder) {
        CalculateShippingPriceRequestDO request = orderMapper.mapOrderToCalculateShippingPriceRequestDO(shippingOrder);
        ShortChargeSummary chargeSummary = rateRepository.calculateShippingPrice(request);

        for (int i = 0; i < shippingOrder.getItems().size(); i++) {
            shippingOrder.getItems().get(i).setShippingPrice(
                    chargeSummary.getItemsPriceList().get(i)
            );
        }

        shippingOrder.setTotalShipmentPrice(chargeSummary.getTotal());
    }

    private boolean validateOrder(Order order) {
        FlightCapacity flightCapacity = flightRepository.getFlightCapacity(order.getFlightId());

        List<Order> flightOrders = orderMapper.mapOrderDOsToOrders(
                orderRepository.findAllByFlightId(order.getFlightId())
        );
    }


}
