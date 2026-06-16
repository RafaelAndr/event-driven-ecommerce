package rafaelandrade.ipurchases.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafaelandrade.ipurchases.orders.entity.Order;
import rafaelandrade.ipurchases.orders.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem>  findByOrder(Order order);
}
