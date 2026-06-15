package rafaelandrade.ipurchases.orders.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafaelandrade.ipurchases.orders.dto.PaymentCallbackReceivementDto;
import rafaelandrade.ipurchases.orders.service.OrderService;

@RestController
@RequestMapping("/orders/callback-payments")
@RequiredArgsConstructor
public class PaymentCallbackReceivementController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> updatePaymentStatus(
            @RequestBody PaymentCallbackReceivementDto body,
            @RequestHeader(required = true, name = "apiKey") String apiKey
            ) {

        orderService.updatePaymentStatus(
                body.code(),
                body.paymentKey(),
                body.status(),
                body.observations()
        );

        return ResponseEntity.ok().build();
    }
}
