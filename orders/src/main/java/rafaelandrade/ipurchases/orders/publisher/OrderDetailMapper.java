package rafaelandrade.ipurchases.orders.publisher;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rafaelandrade.ipurchases.orders.entity.Order;
import rafaelandrade.ipurchases.orders.publisher.representation.OrderDetailRepresentation;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    @Mapping(source = "code", target = "code")
    @Mapping(source = "customerCode", target = "customerCode")
    @Mapping(source = "customer.name", target = "name")
    @Mapping(source = "customer.cpf", target = "cpf")
    @Mapping(source = "customer.address", target = "address")
    @Mapping(source = "customer.number", target = "number")
    @Mapping(source = "customer.district", target = "district")
    @Mapping(source = "customer.email", target = "email")
    @Mapping(source = "customer.telephone", target = "telephone")
    @Mapping(source = "orderDate", target = "orderDate")
    @Mapping(source = "items", target = "items")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "trackingCode", target = "trackingCode")
    @Mapping(source = "urlNf", target = "urlNf")
    OrderDetailRepresentation map(Order order);
}