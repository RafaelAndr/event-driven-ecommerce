package rafaelandrade.ipurchases.invoicing.publisher.representation;

public record UpdateOrderStatus(
        Long code,
        OrderStatus status,
        String urlNf
) {
}
