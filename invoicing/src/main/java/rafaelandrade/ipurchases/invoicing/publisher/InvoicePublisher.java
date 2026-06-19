package rafaelandrade.ipurchases.invoicing.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import rafaelandrade.ipurchases.invoicing.model.Order;
import rafaelandrade.ipurchases.invoicing.publisher.representation.OrderStatus;
import rafaelandrade.ipurchases.invoicing.publisher.representation.UpdateOrderStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoicePublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ipurchases.config.kafka.topics.pedidos-faturados}")
    private String topic;

    public void publish(Order order, String urlNf) {
        try {
            var representation = new UpdateOrderStatus(order.code(), OrderStatus.FATURADO, urlNf);

            String json = objectMapper.writeValueAsString(representation);

            kafkaTemplate.send(topic, "Dados", json);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
