package rafaelandrade.ipurchases.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rafaelandrade.ipurchases.orders.client.BankingServiceClient;
import rafaelandrade.ipurchases.orders.client.CustomerClient;
import rafaelandrade.ipurchases.orders.client.ProductClient;
import rafaelandrade.ipurchases.orders.entity.Order;
import rafaelandrade.ipurchases.orders.entity.OrderItem;
import rafaelandrade.ipurchases.orders.entity.PaymentData;
import rafaelandrade.ipurchases.orders.entity.enums.OrderStatus;
import rafaelandrade.ipurchases.orders.entity.enums.PaymentType;
import rafaelandrade.ipurchases.orders.exception.ItemNotFoundException;
import rafaelandrade.ipurchases.orders.repository.OrderItemRepository;
import rafaelandrade.ipurchases.orders.repository.OrderRepository;
import rafaelandrade.ipurchases.orders.validator.OrderValidator;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidator validator;
    private final BankingServiceClient bankingServiceClient;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

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
        orderItemRepository.saveAll(order.getItems());
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

    public Optional<Order> loadCompleteOrderData(Long code){
        Optional<Order> order = repository.findById(code);
        order.ifPresent(this::loadCustomerData);
        order.ifPresent(this::loadOrderItems);
        return order;
    }

    private void loadCustomerData(Order order) {
        Long customerCode = order.getCustomerCode();
        var response = customerClient.getCustomer(customerCode);
        order.setCustomer(response.getBody());
    }

    private void loadOrderItems(Order order) {
        List<OrderItem> items = orderItemRepository.findByOrder(order);
        order.setItems(items);
        order.getItems().forEach(this::loadProductData);
    }

    private void loadProductData(OrderItem orderItem) {
        Long productCode = orderItem.getProductCode();
        var response = productClient.getProduct(productCode);
        assert response.getBody() != null;
        orderItem.setName(response.getBody().name());
    }
}

