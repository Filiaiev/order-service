package com.filiaiev.orderservice.repository.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Entity(name = "shipping_orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerUserId;

    private Integer flightId;

    private Instant createdAt;

    private BigDecimal totalShipmentPrice;

    // deep dive into correct cascade type and saving complex entities (by fk), mappedBy
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "shippingOrder")
    private List<OrderItemDO> items;

    @Enumerated(value = EnumType.STRING)
    private OrderStatusDO status;
}
