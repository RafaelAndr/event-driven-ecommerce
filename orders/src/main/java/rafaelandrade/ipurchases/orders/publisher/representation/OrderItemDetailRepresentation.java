package rafaelandrade.ipurchases.orders.publisher.representation;

import java.math.BigDecimal;

public record OrderItemDetailRepresentation(
        Long orderCode,
        String name,
        Integer amount,
        BigDecimal unitValue
) {
//    public BigDecimal getTotal(){
//        return unitValue.multiply(BigDecimal.valueOf(amount));
//    }
}
