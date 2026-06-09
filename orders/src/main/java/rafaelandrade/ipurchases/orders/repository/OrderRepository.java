package rafaelandrade.ipurchases.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafaelandrade.ipurchases.orders.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
