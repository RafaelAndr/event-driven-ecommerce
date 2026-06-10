package rafaelandrade.ipurchases.orders.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rafaelandrade.ipurchases.orders.client.BankingServiceClient;
import rafaelandrade.ipurchases.orders.entity.Order;
import rafaelandrade.ipurchases.orders.repository.OrderItemRepositoy;
import rafaelandrade.ipurchases.orders.repository.OrderRepository;
import rafaelandrade.ipurchases.orders.validator.OrderValidator;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderItemRepositoy orderItemRepositoy;
    private final OrderValidator validator;
    private final BankingServiceClient bankingServiceClient;

    @Transactional
    public Order createOrder(Order order){
        validator.validate(order);
        persistData(order);
        sendPaymentRequest(order);
        return order;
    }

    private void sendPaymentRequest(Order order) {
        var paymentKey = bankingServiceClient.requestPayment(order);
        order.setPaymentKey(paymentKey);
    }

    private void persistData(Order order) {
        repository.save(order);
        orderItemRepositoy.saveAll(order.getItems());
    }
}
