package rafaelandrade.ipurchases.logistics.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import rafaelandrade.ipurchases.logistics.model.UpdateOrderDelivery;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderDeliveryPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${ipurchases.config.kafka.topics.pedidos-enviados}")
    private String topic;

    public void publish(UpdateOrderDelivery updateOrderDelivery){
        log.info("Publishing order delivered {}", updateOrderDelivery.code());

        try {
            var json = objectMapper.writeValueAsString(updateOrderDelivery);
            kafkaTemplate.send(topic, "dados", json);

            log.info("Order delivered: {}, tracking code: {}", updateOrderDelivery.code(), updateOrderDelivery.trackingCode());
        } catch (Exception e){
            log.error("Error on publishing order delivery", e);
        }
    }
}
