package rafaelandrade.ipurchases.orders.entity;

public record ResponseError(
        String message,
        String field,
        String error
) {
}
