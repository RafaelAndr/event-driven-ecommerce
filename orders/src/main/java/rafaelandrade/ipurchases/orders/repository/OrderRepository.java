package rafaelandrade.ipurchases.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafaelandrade.ipurchases.orders.entity.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByCodeAndPaymentKey(Long code, String paymentKey);
}
