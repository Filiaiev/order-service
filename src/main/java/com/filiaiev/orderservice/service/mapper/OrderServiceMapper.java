package com.filiaiev.orderservice.service.mapper;

import com.filiaiev.orderservice.model.order.*;
import com.filiaiev.orderservice.repository.entity.charge.CalculateShippingPriceRequestDO;
import com.filiaiev.orderservice.repository.entity.order.OrderDO;
import com.filiaiev.orderservice.repository.entity.order.OrderItemDO;
import com.filiaiev.orderservice.repository.entity.order.OrderStatusDO;
import com.filiaiev.orderservice.repository.entity.order.UpdateOrderStatusDO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderServiceMapper {

    List<OrderItemDO> mapCreateOrderRequestItemsToOrderItemDOs(List<CreateOrderRequestItem> createOrderRequestItems);

    OrderDO mapOrderToOrderDO(Order order);

    CalculateShippingPriceRequestDO mapOrderToCalculateShippingPriceRequestDO(Order order);

    Order mapCreateOrderRequestToOrder(CreateOrderRequest request);

    OrderStatusDO mapOrderStatusToOrderStatusDO(OrderStatus orderStatus);

    Order mapOrderDOToOrder(OrderDO orderDO);

    UpdateOrderStatusDO mapUpdateOrderStatusToUpdateOrderStatusDO(UpdateOrderStatus updateOrderStatus);

    List<Order> mapOrderDOsToOrders(List<OrderDO> orderDOs);
}
