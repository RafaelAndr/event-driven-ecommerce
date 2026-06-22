package rafaelandrade.ipurchases.products.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "unit_value", nullable = false, precision = 16, scale = 2)
    private BigDecimal unitValue;
}
