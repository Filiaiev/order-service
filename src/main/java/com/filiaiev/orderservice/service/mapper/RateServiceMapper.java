package com.filiaiev.orderservice.service.mapper;

import com.filiaiev.orderservice.model.charge.ShortChargeSummary;
import com.filiaiev.orderservice.repository.entity.charge.ShortChargeSummaryDO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RateServiceMapper {

    ShortChargeSummary mapShortChargeSummaryDOToShortChargeSummary(ShortChargeSummaryDO shortChargeSummaryDO);
}
