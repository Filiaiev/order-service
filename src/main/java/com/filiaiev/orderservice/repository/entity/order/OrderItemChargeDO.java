package com.filiaiev.orderservice.repository.entity.order;

import com.filiaiev.orderservice.model.order.OrderItem;
import com.filiaiev.orderservice.repository.entity.charge.ChargeTypeDO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Entity(name = "order_item_charges")
@Data
@ToString(exclude = {"orderItem"})
public class OrderItemChargeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OrderItemDO orderItem;

    @Enumerated(value = EnumType.STRING)
    private ChargeTypeDO chargeType;

    private BigDecimal charge;
}
