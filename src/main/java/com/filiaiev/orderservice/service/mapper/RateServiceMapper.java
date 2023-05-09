package com.filiaiev.orderservice.service.mapper;

import com.filiaiev.orderservice.model.charge.ChargeSummary;
import com.filiaiev.orderservice.model.charge.ItemCharge;
import com.filiaiev.orderservice.repository.entity.charge.ChargeSummaryDO;
import com.filiaiev.orderservice.repository.entity.charge.ItemChargeDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RateServiceMapper {

    ChargeSummary mapChargeSummaryDOToChargeSummary(ChargeSummaryDO chargeSummaryDO);

    @Mapping(target = "chargeType", source = "type")
    ItemCharge mapItemChargeDOToItemCharge(ItemChargeDO itemChargeDO);
}
