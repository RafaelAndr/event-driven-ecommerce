package rafaelandrade.ipurchases.invoicing.mapper;

import org.springframework.stereotype.Component;
import rafaelandrade.ipurchases.invoicing.model.Customer;
import rafaelandrade.ipurchases.invoicing.model.Order;
import rafaelandrade.ipurchases.invoicing.model.OrderItem;
import rafaelandrade.ipurchases.invoicing.subscriber.representation.OrderDetailRepresentation;
import rafaelandrade.ipurchases.invoicing.subscriber.representation.OrderItemDetailRepresentation;

import java.util.List;

@Component
public class OrderMapper {

    public Order map(OrderDetailRepresentation representation){
        Customer customer = new Customer(
                representation.name(),
                representation.cpf(),
                representation.address(),
                representation.number(),
                representation.district(),
                representation.email(),
                representation.telephone()
        );

        List<OrderItem> items = representation.items().stream().map(this::mapItem).toList();

        return new Order(
                representation.code(),
                customer,
                representation.orderDate(),
                representation.total(),
                items
        );
    }

    private OrderItem mapItem(OrderItemDetailRepresentation representation){
        return new OrderItem(
                representation.orderCode(),
                representation.name(),
                representation.unitValue(),
                representation.amount(),
                representation.total()
                );
    }
}
