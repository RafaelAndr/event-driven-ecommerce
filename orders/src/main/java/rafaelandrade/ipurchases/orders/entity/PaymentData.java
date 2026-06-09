package rafaelandrade.ipurchases.orders.entity;

import lombok.Data;
import rafaelandrade.ipurchases.orders.entity.enums.PaymentType;

@Data
public class PaymentData {
    private String data;
    private PaymentType paymentType;
}
