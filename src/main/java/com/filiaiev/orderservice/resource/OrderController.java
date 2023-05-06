package com.filiaiev.orderservice.resource;

import com.filiaiev.orderservice.model.order.CreateOrderRequest;
import com.filiaiev.orderservice.model.order.UpdateOrderStatus;
import com.filiaiev.orderservice.resource.mapper.OrderResourceMapper;
import com.filiaiev.orderservice.resource.ro.CreateOrderRequestRO;
import com.filiaiev.orderservice.resource.ro.OrderDetailedRO;
import com.filiaiev.orderservice.resource.ro.OrderShortRO;
import com.filiaiev.orderservice.resource.ro.UpdateOrderStatusRO;
import com.filiaiev.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderResourceMapper orderMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderRequestRO createOrderRequestRO) {
        CreateOrderRequest createOrderRequest =
                orderMapper.mapCreateOrderRequestROToCreateOrderRequest(createOrderRequestRO);

        orderService.createOrder(createOrderRequest);
    }

    @GetMapping(value = "/{orderId}", produces = "application/order-short+json")
    public OrderShortRO getOrderShort(@PathVariable Integer orderId) {
        return orderMapper.mapOrderToOrderShortRO(
                orderService.getOrder(orderId)
        );
    }

    @GetMapping(value = "/{orderId}", produces = "application/order-detailed+json")
    public OrderDetailedRO getOrderDetailed(@PathVariable Integer orderId) {
        return orderMapper.mapOrderToOrderDetailedRO(
                orderService.getOrder(orderId)
        );
    }

    // Add additional filters
    @GetMapping
    public List<OrderShortRO> getUserOrders(@RequestParam Integer userId) {
        return orderMapper.mapOrdersToOrderShortROs(
                orderService.getOrders(userId)
        );
    }

    @PatchMapping("/{orderId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderStatus(@PathVariable Integer orderId, @RequestBody UpdateOrderStatusRO updateRequestRO) {
        UpdateOrderStatus updateRequest = orderMapper.mapUpdateOrderStatusROToUpdateOrderStatus(updateRequestRO);
        orderService.updateOrderStatus(orderId, updateRequest);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable Integer orderId) {
        orderService.cancelOrder(orderId);
    }
}
