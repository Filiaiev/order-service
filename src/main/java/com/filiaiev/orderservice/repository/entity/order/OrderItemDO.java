package com.filiaiev.orderservice.repository.entity.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity(name = "shipping_order_items")
public class OrderItemDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private OrderDO shippingOrder;

    private Integer quantity;

    private Double declaredWeight;

    private Double height;

    private Double width;

    private Double length;

    private BigDecimal declaredCargoPrice;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "orderItem")
    private List<OrderItemChargeDO> itemCharges;
}
