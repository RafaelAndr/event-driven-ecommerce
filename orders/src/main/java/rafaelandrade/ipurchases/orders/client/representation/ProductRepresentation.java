package rafaelandrade.ipurchases.orders.client.representation;

import java.math.BigDecimal;

public record ProductRepresentation(
        long code,
        String name,
        BigDecimal unitValue
) {
}
