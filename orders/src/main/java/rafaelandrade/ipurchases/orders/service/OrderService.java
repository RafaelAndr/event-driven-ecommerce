package rafaelandrade.ipurchases.orders.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public Order createOrder(Order order){
        repository.save(order);
        orderItemRepositoy.saveAll(order.getItems());
        return order;
    }
}
