package com.filiaiev.orderservice.resource.mapper;

import com.filiaiev.orderservice.model.order.CreateOrderRequest;
import com.filiaiev.orderservice.model.order.Order;
import com.filiaiev.orderservice.model.order.UpdateOrderStatus;
import com.filiaiev.orderservice.resource.ro.CreateOrderRequestRO;
import com.filiaiev.orderservice.resource.ro.OrderDetailedRO;
import com.filiaiev.orderservice.resource.ro.OrderShortRO;
import com.filiaiev.orderservice.resource.ro.UpdateOrderStatusRO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderResourceMapper {

    @Mapping(target = "customerUserId", constant = "1")
    CreateOrderRequest mapCreateOrderRequestROToCreateOrderRequest(CreateOrderRequestRO request);

    UpdateOrderStatus mapUpdateOrderStatusROToUpdateOrderStatus(UpdateOrderStatusRO updateOrderStatusRO);

    OrderShortRO mapOrderToOrderShortRO(Order order);

    OrderDetailedRO mapOrderToOrderDetailedRO(Order order);

    List<OrderShortRO> mapOrdersToOrderShortROs(List<Order> orders);
}
