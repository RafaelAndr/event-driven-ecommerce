package rafaelandrade.ipurchases.orders.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafaelandrade.ipurchases.orders.dto.NewOrderDto;
import rafaelandrade.ipurchases.orders.dto.NewPaymentDto;
import rafaelandrade.ipurchases.orders.entity.ResponseError;
import rafaelandrade.ipurchases.orders.exception.ItemNotFoundException;
import rafaelandrade.ipurchases.orders.exception.ValidateException;
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
        try {
            var order = mapper.map(dto);
            var newOrder = service.createOrder(order);
            return ResponseEntity.ok(newOrder.getCode());
        } catch (ValidateException e) {
            var error = new ResponseError("Validation Error", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/payments")
    public ResponseEntity<Object> addNewPayment(@RequestBody NewPaymentDto dto){
        try {
            service.addNewPayment(dto.orderCode(), dto.data(), dto.paymentType());
            return ResponseEntity.noContent().build();
        } catch (ItemNotFoundException e) {
            var error = new ResponseError("Item not found", "orderCode", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
