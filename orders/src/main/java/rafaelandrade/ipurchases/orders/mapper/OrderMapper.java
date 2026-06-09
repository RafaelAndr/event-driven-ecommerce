package rafaelandrade.ipurchases.orders.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import rafaelandrade.ipurchases.orders.dto.NewOrderDto;
import rafaelandrade.ipurchases.orders.dto.OrderItemDto;
import rafaelandrade.ipurchases.orders.entity.Order;
import rafaelandrade.ipurchases.orders.entity.OrderItem;
import rafaelandrade.ipurchases.orders.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderItemMapper ORDER_ITEM_MAPPER = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "items", target = "items", qualifiedByName = "mapItems")
    @Mapping(source = "paymentData", target = "paymentData")
    Order map(NewOrderDto dto);

    @Named("mapItems")
    default List<OrderItem> mapItems(List<OrderItemDto> dtos){
        return dtos.stream().map(ORDER_ITEM_MAPPER::map).toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Order order){
        order.setStatus(OrderStatus.REALIZADO);
        order.setOrderDate(LocalDateTime.now());

        var total = calculateTotal(order);
        order.setTotal(total);
        order.getItems().forEach(item -> item.setOrder(order));
    }

    private static BigDecimal calculateTotal(Order order){
        return order.getItems().stream().map(
                item -> item.getUnitValue().multiply(BigDecimal.valueOf(item.getAmount()))
        ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
    }
}
