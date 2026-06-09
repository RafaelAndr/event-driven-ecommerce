package rafaelandrade.ipurchases.orders.dto;

import java.math.BigDecimal;

public record OrderItemDto(
    Long productCode,
    Integer amount,
    BigDecimal unitValue
) {
}
