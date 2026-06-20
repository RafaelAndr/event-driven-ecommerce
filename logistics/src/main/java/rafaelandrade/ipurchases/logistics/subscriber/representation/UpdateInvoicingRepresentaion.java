package rafaelandrade.ipurchases.logistics.subscriber.representation;

import rafaelandrade.ipurchases.logistics.model.OrderStatus;

public record UpdateInvoicingRepresentaion(
        Long code,
        OrderStatus status,
        String urlNf
) {
}
