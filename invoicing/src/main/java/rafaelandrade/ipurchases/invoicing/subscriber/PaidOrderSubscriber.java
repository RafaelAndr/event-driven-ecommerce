package rafaelandrade.ipurchases.invoicing.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rafaelandrade.ipurchases.invoicing.mapper.OrderMapper;
import rafaelandrade.ipurchases.invoicing.model.Order;
import rafaelandrade.ipurchases.invoicing.service.InvoiceGeneratorService;
import rafaelandrade.ipurchases.invoicing.subscriber.representation.OrderDetailRepresentation;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaidOrderSubscriber {

    private final ObjectMapper mapper;
    private final InvoiceGeneratorService service;
    private final OrderMapper orderMapper;

    @KafkaListener(groupId = "ipurchases-invoicing", topics = "${ipurchases.config.kafka.topics.pedidos-pagos}")
    public void listen(String json){
        try {
            log.info("Recebendo pedido para faturamento: {}", json);
            var representation = mapper.readValue(json, OrderDetailRepresentation.class);
            Order order = orderMapper.map(representation);
            service.generateInvoice(order);

        } catch (Exception e){
            log.error("Erro na consumação do topico de pedidos pagos: {} ", e.getMessage());
        }
    }
}