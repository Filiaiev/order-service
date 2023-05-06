package com.filiaiev.orderservice.service.mapper;

import com.filiaiev.orderservice.model.flight.Flight;
import com.filiaiev.orderservice.model.order.*;
import com.filiaiev.orderservice.repository.entity.charge.CalculateShippingPriceRequestDO;
import com.filiaiev.orderservice.repository.entity.order.OrderDO;
import com.filiaiev.orderservice.repository.entity.order.OrderItemDO;
import com.filiaiev.orderservice.repository.entity.order.OrderStatusDO;
import com.filiaiev.orderservice.repository.entity.order.UpdateOrderStatusDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderServiceMapper {

    default OrderDO mapOrderToOrderDO(Order order) {
        OrderDO orderDO = OrderDO.builder()
                .id(order.getId())
                .flightId(order.getFlightId())
                .customerUserId(order.getCustomerUserId())
                .createdAt(Instant.now())
                .totalShipmentPrice(order.getTotalShipmentPrice())
                .status(mapOrderStatusToOrderStatusDO(order.getStatus()))
                .build();

        orderDO.setItems(mapOrderItemsToOrderItemDOs(order.getItems(), orderDO));

        return orderDO;
    }

    @Mapping(target = "zoneRouteId", source = "flight.chargeableZoneRouteId")
    CalculateShippingPriceRequestDO mapOrderToCalculateShippingPriceRequestDO(Order order, Flight flight);

    @Mapping(target = "status", constant = "AWAITING_WAREHOUSE_SHIPPING")
    Order mapCreateOrderRequestToOrder(CreateOrderRequest request);

    OrderStatusDO mapOrderStatusToOrderStatusDO(OrderStatus orderStatus);

    Order mapOrderDOToOrder(OrderDO orderDO);

    UpdateOrderStatusDO mapUpdateOrderStatusToUpdateOrderStatusDO(UpdateOrderStatus updateOrderStatus);

    List<Order> mapOrderDOsToOrders(List<OrderDO> orderDOs);

    List<OrderItemDO> mapOrderItemsToOrderItemDOs(List<OrderItem> orderItems);

    default List<OrderItemDO> mapOrderItemsToOrderItemDOs(List<OrderItem> orderItems, OrderDO orderDO) {
        List<OrderItemDO> orderItemDOs = mapOrderItemsToOrderItemDOs(orderItems);

        orderItemDOs.forEach(order -> order.setShippingOrder(orderDO));

        return orderItemDOs;
    }
}
