package rafaelandrade.ipurchases.orders.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rafaelandrade.ipurchases.orders.service.UpdateOrderStatusService;
import rafaelandrade.ipurchases.orders.subscriber.representation.UpdateOrderStatusRepresentation;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateOrderStatusSubscriber {

    private final UpdateOrderStatusService service;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = {
                    "${ipurchases.config.kafka.topics.pedidos-faturados}",
                    "${ipurchases.config.kafka.topics.pedidos-enviados}"
            }
    )
    public void receiveUpdating(String json){
        log.info("Receiving updating {}", json);

        try {
            var updatingStatus = objectMapper.readValue(json, UpdateOrderStatusRepresentation.class);
            service.updateStatus(
                    updatingStatus.code(),
                    updatingStatus.status(),
                    updatingStatus.urlNf(),
                    updatingStatus.trackingCode());

            log.info("Order successfully updated");

        } catch (Exception e) {
            log.error("Error on updating order status: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}