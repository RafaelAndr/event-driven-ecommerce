package rafaelandrade.ipurchases.invoicing.model;

import java.math.BigDecimal;
import java.util.List;

public record Order(
        Long code,
        Customer customer,
        String date,
        BigDecimal total,
        List<OrderItem> items
) {
}
