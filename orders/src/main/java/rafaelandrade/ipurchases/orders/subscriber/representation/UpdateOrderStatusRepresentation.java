package rafaelandrade.ipurchases.orders.subscriber.representation;

import rafaelandrade.ipurchases.orders.entity.enums.OrderStatus;

public record UpdateOrderStatusRepresentation(
        Long code,
        OrderStatus status,
        String urlNf,
        String trackingCode
) {
}
