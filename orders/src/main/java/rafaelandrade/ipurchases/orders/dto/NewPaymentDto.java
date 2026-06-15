package rafaelandrade.ipurchases.orders.dto;

import rafaelandrade.ipurchases.orders.entity.enums.PaymentType;

public record NewPaymentDto(
        Long orderCode,
        String data,
        PaymentType paymentType
) {
}
