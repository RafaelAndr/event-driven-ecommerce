package rafaelandrade.ipurchases.logistics.model;

public record UpdateOrderDelivery(
        Long code,
        OrderStatus status,
        String trackingCode
) {
}
