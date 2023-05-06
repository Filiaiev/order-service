package com.filiaiev.orderservice.repository;

import com.filiaiev.orderservice.model.charge.CalculateShippingPriceRequest;
import com.filiaiev.orderservice.model.charge.ShortChargeSummary;
import com.filiaiev.orderservice.repository.entity.charge.CalculateShippingPriceRequestDO;
import com.filiaiev.orderservice.repository.entity.charge.ShortChargeSummaryDO;
import com.filiaiev.orderservice.service.mapper.RateServiceMapper;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;

@Repository
public class RateRepository extends ApiRepository {

    public static final String PRICING_PATH = "/pricing";

    private final RateServiceMapper rateServiceMapper;

    public RateRepository(@Value("${rate-service.base-url}") String baseUrl,
                          @Value("${logging.level.webclient}") LogLevel logLevel,
                          @Autowired RateServiceMapper rateServiceMapper) {
        super(baseUrl, logLevel);
        this.rateServiceMapper = rateServiceMapper;
    }

    public ShortChargeSummary calculateShippingPrice(CalculateShippingPriceRequestDO request) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(PRICING_PATH).build())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept", "application/summary-short+json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShortChargeSummaryDO.class)
                .map(rateServiceMapper::mapShortChargeSummaryDOToShortChargeSummary)
                .block();
    }
}
