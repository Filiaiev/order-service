package com.filiaiev.orderservice.repository.entity.charge;

import lombok.Data;

import java.util.List;

@Data
public class CreateChargeSummaryRequestDO {

    private Integer zoneRouteId;
    private List<CreateChargeSummaryRequestItemDO> items;
}
