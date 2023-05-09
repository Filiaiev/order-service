package com.filiaiev.orderservice.service.mapper;

import com.filiaiev.orderservice.model.charge.ChargeSummary;
import com.filiaiev.orderservice.model.charge.ItemCharge;
import com.filiaiev.orderservice.model.flight.Flight;
import com.filiaiev.orderservice.model.order.*;
import com.filiaiev.orderservice.repository.entity.charge.CreateChargeSummaryRequestDO;
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
    CreateChargeSummaryRequestDO mapOrderToCalculateShippingPriceRequestDO(Order order, Flight flight);

    @Mapping(target = "status", constant = "AWAITING_WAREHOUSE_SHIPPING")
    Order mapCreateOrderRequestToOrder(CreateOrderRequest request);

    OrderStatusDO mapOrderStatusToOrderStatusDO(OrderStatus orderStatus);

    Order mapOrderDOToOrder(OrderDO orderDO);

    UpdateOrderStatusDO mapUpdateOrderStatusToUpdateOrderStatusDO(UpdateOrderStatus updateOrderStatus);

    List<Order> mapOrderDOsToOrders(List<OrderDO> orderDOs);

    List<OrderItemDO> mapOrderItemsToOrderItemDOs(List<OrderItem> orderItems);

    @Mapping(target = "height", source = "dimension.height")
    @Mapping(target = "width", source = "dimension.width")
    @Mapping(target = "length", source = "dimension.length")
    OrderItemDO mapOrderItemTOOrderItemDO(OrderItem orderItem);

    @Mapping(source = "height", target = "dimension.height")
    @Mapping(source = "width", target = "dimension.width")
    @Mapping(source = "length", target = "dimension.length")
    OrderItem mapOrderItemDOToOrderItem(OrderItemDO orderItemDO);

    default List<OrderItemDO> mapOrderItemsToOrderItemDOs(List<OrderItem> orderItems, OrderDO orderDO) {
        List<OrderItemDO> orderItemDOs = mapOrderItemsToOrderItemDOs(orderItems);

        orderItemDOs.forEach(order -> order.setShippingOrder(orderDO));

        for (OrderItemDO item : orderItemDOs) {
            item.setShippingOrder(orderDO);
            item.getItemCharges().forEach(ic -> ic.setOrderItem(item));
        }

        return orderItemDOs;
    }

    default List<OrderItemCharge> mapChargeSummaryToOrderItemCharges(ChargeSummary chargeSummary, int index) {
        if (chargeSummary.getItemsBreakdown() == null) {
            return null;
        }

        return mapItemChargesToOrderItemCharges(
                chargeSummary.getItemsBreakdown().get(index).getItemBreakdown()
        );
    }

    List<OrderItemCharge> mapItemChargesToOrderItemCharges(List<ItemCharge> itemCharges);

}
