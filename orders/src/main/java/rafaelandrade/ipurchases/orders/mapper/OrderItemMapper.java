package rafaelandrade.ipurchases.orders.mapper;

import org.mapstruct.Mapper;
import rafaelandrade.ipurchases.orders.dto.OrderItemDto;
import rafaelandrade.ipurchases.orders.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem map(OrderItemDto dto);
}
