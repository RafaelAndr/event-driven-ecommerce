package rafaelandrade.ipurchases.orders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @JoinColumn(name = "order_code")
    @ManyToOne
    private Order order;

    @Column(name = "product_code", nullable = false)
    private Long productCode;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "unit_value", nullable = false, precision = 16, scale = 2)
    private BigDecimal unitValue;

    @Transient
    private String name;
}
