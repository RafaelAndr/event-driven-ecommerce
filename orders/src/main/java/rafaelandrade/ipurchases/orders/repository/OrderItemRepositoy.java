package rafaelandrade.ipurchases.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafaelandrade.ipurchases.orders.entity.OrderItem;

public interface OrderItemRepositoy extends JpaRepository<OrderItem, Long> {
}
