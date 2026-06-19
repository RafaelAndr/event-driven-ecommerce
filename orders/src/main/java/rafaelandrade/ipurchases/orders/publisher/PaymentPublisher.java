package rafaelandrade.ipurchases.orders.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import rafaelandrade.ipurchases.orders.entity.Order;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PaymentPublisher {
    private final OrderDetailMapper orderDetailMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${ipurchases.config.kafka.topics.pedidos-pagos}")
    private String topic;

    public void publish(Order order){
        log.info("Publishing paid order {}", order.getCode());

        try {
            var representation = orderDetailMapper.map(order);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topic, "Data", json);
        } catch (JsonProcessingException e){
            log.error("Error on processing json", e);
        } catch (RuntimeException e){
            log.error("Unexpected error ", e);
        }
    }
}
