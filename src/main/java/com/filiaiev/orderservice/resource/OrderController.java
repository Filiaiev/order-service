package com.filiaiev.orderservice.resource;

import com.filiaiev.orderservice.model.order.Order;
import com.filiaiev.orderservice.model.order.UpdateOrderStatus;
import com.filiaiev.orderservice.resource.mapper.OrderResourceMapper;
import com.filiaiev.orderservice.resource.entity.CreateOrderRequestRO;
import com.filiaiev.orderservice.resource.entity.OrderDetailedRO;
import com.filiaiev.orderservice.resource.entity.OrderShortRO;
import com.filiaiev.orderservice.resource.entity.UpdateOrderStatusRO;
import com.filiaiev.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderResourceMapper orderMapper;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderRequestRO createOrderRequestRO, Authentication authentication) {
        Order createOrder = orderMapper.mapCreateOrderRequestROToOrder(createOrderRequestRO, authentication);

        orderService.createOrder(createOrder);
    }

    @GetMapping(value = "/{orderId}", produces = "application/order-short+json")
    public OrderShortRO getOrderShort(@PathVariable Integer orderId, Authentication authentication) {
        return orderMapper.mapOrderToOrderShortRO(
                orderService.getOrder(orderId, authentication)
        );
    }

    @GetMapping(value = "/{orderId}", produces = "application/order-detailed+json")
    public OrderDetailedRO getOrderDetailed(@PathVariable Integer orderId, Authentication authentication) {
        return orderMapper.mapOrderToOrderDetailedRO(
                orderService.getOrder(orderId, authentication)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasRole('CUSTOMER') and principal == #userId")
    @GetMapping
    public List<OrderShortRO> getOrders(@RequestParam Integer userId) {
        return orderMapper.mapOrdersToOrderShortROs(
                orderService.getOrders(userId)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PatchMapping("/{orderId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderStatus(@PathVariable Integer orderId, @RequestBody UpdateOrderStatusRO updateRequestRO) {
        UpdateOrderStatus updateRequest = orderMapper.mapUpdateOrderStatusROToUpdateOrderStatus(updateRequestRO);
        orderService.updateOrderStatus(orderId, updateRequest);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable Integer orderId, Authentication authentication) {
        orderService.cancelOrder(orderId, authentication);
    }
}
