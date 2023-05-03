package com.filiaiev.orderservice.repository.entity.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderStatusDO {

    private OrderStatusDO orderStatus;
}
