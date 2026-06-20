package rafaelandrade.ipurchases.logistics.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rafaelandrade.ipurchases.logistics.service.OrderDeliveryService;
import rafaelandrade.ipurchases.logistics.subscriber.representation.UpdateInvoicingRepresentaion;

@Component
@Slf4j
@RequiredArgsConstructor
public class InvoicingSubscriber {

    private final ObjectMapper objectMapper;
    private final OrderDeliveryService service;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${ipurchases.config.kafka.topics.pedidos-faturados}")
    public void listen(String json){
        log.info("Receiving order to delivery: {}", json);

        try {
            var representation = objectMapper.readValue(json, UpdateInvoicingRepresentaion.class);

            service.delivery(representation.code(), representation.urlNf());

            log.info("Order successfully delivered: {}", representation.code());

        } catch (Exception e) {
            log.error("Error on preparing order to deliver", e);
        }
    }
}
