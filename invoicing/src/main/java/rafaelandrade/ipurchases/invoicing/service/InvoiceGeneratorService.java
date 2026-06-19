package rafaelandrade.ipurchases.invoicing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import rafaelandrade.ipurchases.invoicing.bucket.BucketFile;
import rafaelandrade.ipurchases.invoicing.bucket.BucketService;
import rafaelandrade.ipurchases.invoicing.model.Order;

import java.io.ByteArrayInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceGeneratorService {

    private final InvoiceService invoiceService;
    private final BucketService bucketService;

    public void generate(Order order) {
        log.info("Gerando nota fiscal para o pedido {}", order.code());

        try {
            byte[] byteArray = invoiceService.generateNotaFiscal(order);

            String fileName = String.format("notafiscal_pedido_%d.pdf", order.code());

            var file = new BucketFile(fileName, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, byteArray.length);

            bucketService.upload(file);

            log.info("Gerada a nota fiscal, nome arquivo: {}", fileName);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
