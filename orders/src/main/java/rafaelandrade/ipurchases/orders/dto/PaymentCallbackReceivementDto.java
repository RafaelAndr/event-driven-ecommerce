package rafaelandrade.ipurchases.orders.dto;

public record PaymentCallbackReceivementDto(
        Long code,
        String paymentKey,
        boolean status,
        String observations
) {
}
