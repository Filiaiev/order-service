package com.filiaiev.orderservice.repository;

import com.filiaiev.orderservice.repository.entity.order.OrderDO;
import com.filiaiev.orderservice.repository.entity.order.UpdateOrderStatusDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDO, Integer> {

    List<OrderDO> findAllByCustomerUserId(Integer userId);

    List<OrderDO> findAllByFlightId(Integer flightId);

    default OrderDO updateOrderStatus(Integer id, UpdateOrderStatusDO updateRequest) {
        OrderDO order = getReferenceById(id);
        order.setStatus(updateRequest.getOrderStatus());
        return save(order);
    }
}
