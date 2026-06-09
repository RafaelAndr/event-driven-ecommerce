package rafaelandrade.ipurchases.orders.dto;

import rafaelandrade.ipurchases.orders.entity.enums.PaymentType;

public record PaymentDataDto(
        PaymentType paymentType
) {
}
