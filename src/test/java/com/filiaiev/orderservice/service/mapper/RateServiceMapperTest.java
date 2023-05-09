package com.filiaiev.orderservice.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RateServiceMapperTest {

    private final RateServiceMapper rateMapper = new RateServiceMapperImpl();

//    @Test
//    public void testMapCreateOrderRequestToCalculateShippingPriceRequestDO() {
//        int zoneRouteId = 1;
//        CreateOrderRequestItem expectedItem = CreateOrderRequestItem.builder()
//                .quantity(2)
//                .actualWeight(350.5)
//                .height(1.75)
//                .width(1.05)
//                .length(2.35)
//                .declaredCargoPrice(BigDecimal.valueOf(1250))
//                .build();
//
//        List<CreateOrderRequestItem> items = Collections.singletonList(expectedItem);
//
//        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
//        createOrderRequest.setZoneRouteId(zoneRouteId);
//        createOrderRequest.setItems(items);
//
//        CreateChargeSummaryRequestDO actual =
//                rateMapper.mapCreateOrderRequestToOrder(createOrderRequest);
//
//        CreateChargeSummaryRequestItemDO actualItem = actual.getItems().get(0);
//
//        assertEquals(1, actual.getZoneRouteId());
//        assertEquals(1, actual.getItems().size());
//        assertEquals(expectedItem.getQuantity(), actualItem.getQuantity());
//        assertEquals(expectedItem.getActualWeight(), actualItem.getActualWeight());
//        assertEquals(expectedItem.getHeight(), actualItem.getHeight());
//        assertEquals(expectedItem.getWidth(), actualItem.getWidth());
//        assertEquals(expectedItem.getLength(), actualItem.getLength());
//        assertEquals(expectedItem.getDeclaredCargoPrice(), actualItem.getDeclaredCargoPrice());
//    }
}
