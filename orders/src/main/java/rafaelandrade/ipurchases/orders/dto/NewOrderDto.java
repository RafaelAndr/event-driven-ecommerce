package rafaelandrade.ipurchases.orders.dto;

import java.util.List;

public record NewOrderDto(
    Long customerCode,
    PaymentDataDto paymentData,
    List<OrderItemDto> items

) {
}
