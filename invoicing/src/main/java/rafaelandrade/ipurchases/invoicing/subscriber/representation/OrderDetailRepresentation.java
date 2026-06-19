package rafaelandrade.ipurchases.invoicing.subscriber.representation;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetailRepresentation(
        Long code,
        Long customerCode,
        String name,
        String cpf,
        String address,
        String number,
        String district,
        String email,
        String telephone,
        String orderDate,
        BigDecimal total,
        String urlNf,
        String trackingCode,
        List<OrderItemDetailRepresentation> items
) {
}
