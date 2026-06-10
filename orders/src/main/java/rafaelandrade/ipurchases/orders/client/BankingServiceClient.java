package rafaelandrade.ipurchases.orders.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rafaelandrade.ipurchases.orders.entity.Order;

import java.util.UUID;

@Component
@Slf4j
public class BankingServiceClient {

    public String requestPayment(Order order){
        log.info("Requesting payment for the order: {}", order.getCode());
        return UUID.randomUUID().toString();
    }
}

