package rafaelandrade.ipurchases.orders.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafaelandrade.ipurchases.orders.dto.NewOrderDto;
import rafaelandrade.ipurchases.orders.mapper.OrderMapper;
import rafaelandrade.ipurchases.orders.service.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final OrderMapper mapper;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody NewOrderDto dto){
        var order = mapper.map(dto);
        var newOrder = service.createOrder(order);
        return ResponseEntity.ok(newOrder.getCode());
    }
}
