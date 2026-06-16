package rafaelandrade.ipurchases.orders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rafaelandrade.ipurchases.orders.client.representation.CustomerRepresentation;
import rafaelandrade.ipurchases.orders.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(name = "customer_code", nullable = false)
    private Long customerCode;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "observations")
    private String observations;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private OrderStatus status;

    @Column(name = "total", nullable = false, precision = 16, scale = 2)
    private BigDecimal total;

    @Column(name = "tracking_code", length = 255)
    private String trackingCode;

    @Column(name = "url_nf")
    private String urlNf;

    @Transient
    private PaymentData paymentData;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @Transient
    private CustomerRepresentation customer;
}
