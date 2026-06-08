package rafaelandrade.ipurchases.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafaelandrade.ipurchases.products.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
