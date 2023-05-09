package com.filiaiev.orderservice.resource.mapper;

import com.filiaiev.orderservice.model.order.*;
import com.filiaiev.orderservice.resource.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderResourceMapper {

    @Mapping(target = "customerUserId", constant = "1")
    CreateOrderRequest mapCreateOrderRequestROToCreateOrderRequest(CreateOrderRequestRO request);

    @Mapping(target = "status", constant = "AWAITING_WAREHOUSE_SHIPPING")
    @Mapping(target = "customerUserId", constant = "1")
    Order mapCreateOrderRequestROToOrder(CreateOrderRequestRO requestRO);

    UpdateOrderStatus mapUpdateOrderStatusROToUpdateOrderStatus(UpdateOrderStatusRO updateOrderStatusRO);

    OrderShortRO mapOrderToOrderShortRO(Order order);

    OrderDetailedRO mapOrderToOrderDetailedRO(Order order);

    @Mapping(target = "shippingPrice", source = "itemCharges",
            qualifiedByName = "mapChargesListToShippingPrice")
    OrderItemRO mapOrderItemToOrderItemRO(OrderItem orderItem);

    @Named("mapChargesListToShippingPrice")
    default BigDecimal mapChargesListToShippingPrice(List<OrderItemCharge> itemCharges) {
        return itemCharges.stream()
                .map(OrderItemCharge::getCharge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    List<OrderShortRO> mapOrdersToOrderShortROs(List<Order> orders);
}
