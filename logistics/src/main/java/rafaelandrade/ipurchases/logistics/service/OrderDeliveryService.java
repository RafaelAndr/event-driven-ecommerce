package rafaelandrade.ipurchases.logistics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rafaelandrade.ipurchases.logistics.model.OrderStatus;
import rafaelandrade.ipurchases.logistics.model.UpdateOrderDelivery;
import rafaelandrade.ipurchases.logistics.publisher.OrderDeliveryPublisher;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderDeliveryService {

    private final OrderDeliveryPublisher publisher;

    public void delivery(Long orderCode, String urlNf){
        var trackingCode = generateTrackingCode();
        var updatingRepresentation = new UpdateOrderDelivery(orderCode, OrderStatus.ENVIADO, trackingCode);
        publisher.publish(updatingRepresentation);
    }

    private String generateTrackingCode(){
        var random = new Random();

        char letter1 = (char) ('A' + random.nextInt(26));
        char letter2 = (char) ('A' + random.nextInt(26));

        int numbers = 1000000000 + random.nextInt(900000000);

        return "" + letter1 + letter2 + numbers + "BR";
    }
}
