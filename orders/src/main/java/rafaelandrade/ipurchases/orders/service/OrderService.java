package rafaelandrade.ipurchases.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rafaelandrade.ipurchases.orders.client.BankingServiceClient;
import rafaelandrade.ipurchases.orders.entity.Order;
import rafaelandrade.ipurchases.orders.entity.PaymentData;
import rafaelandrade.ipurchases.orders.entity.enums.OrderStatus;
import rafaelandrade.ipurchases.orders.entity.enums.PaymentType;
import rafaelandrade.ipurchases.orders.exception.ItemNotFoundException;
import rafaelandrade.ipurchases.orders.repository.OrderItemRepositoy;
import rafaelandrade.ipurchases.orders.repository.OrderRepository;
import rafaelandrade.ipurchases.orders.validator.OrderValidator;

@Service
@Slf4j
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

    public void updatePaymentStatus(Long code, String paymentKey, boolean success, String observations) {
        var orderFound = repository.findByCodeAndPaymentKey(code, paymentKey);

        if (orderFound.isEmpty()){
            var msg = String.format("Order not founded for code %d and paymentKey %s", code, paymentKey);
            log.error(msg);
            return;
        }

        Order order = orderFound.get();

        if (success) {
            order.setStatus(OrderStatus.PAGO);
        } else {
            order.setStatus(OrderStatus.ERRO_PAGAMENTO);
            order.setObservations(observations);
        }
        repository.save(order);
    }

    @Transactional
    public void addNewPayment(Long orderCode, String data, PaymentType type){
        var orderFound = repository.findById(orderCode);

        if (orderFound.isEmpty()){
            throw new ItemNotFoundException("Order not found");
        }

        var order = orderFound.get();

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentType(type);
        paymentData.setData(data);

        order.setPaymentData(paymentData);
        order.setStatus(OrderStatus.REALIZADO);
        order.setObservations("New payment finished, waiting processing");

        String newPaymentKey = bankingServiceClient.requestPayment(order);
        order.setPaymentKey(newPaymentKey);

        repository.save(order);
    }
}

