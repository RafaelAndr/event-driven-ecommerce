package rafaelandrade.ipurchases.invoicing.subscriber.representation;

import java.math.BigDecimal;

public record OrderItemDetailRepresentation(
        Long orderCode,
        String name,
        Integer amount,
        BigDecimal unitValue,
        BigDecimal total
) {

}
