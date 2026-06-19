package rafaelandrade.ipurchases.invoicing.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import rafaelandrade.ipurchases.invoicing.model.Order;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class InvoiceService {

    @Value("classpath:report/nota-fiscal.jrxml")
    private Resource notaFiscal;

    @Value("classpath:report/logo.png")
    private Resource logo;

    public byte[] generateNotaFiscal(Order order){
        try (InputStream inputStream = notaFiscal.getInputStream()) {

            Map<String, Object> params = getStringObjectMap(order);

            var dataSource = new JRBeanCollectionDataSource(order.items());

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> getStringObjectMap(Order order) throws IOException {
        Map<String, Object> params = new HashMap<>();

        params.put("NOME", order.customer().name());
        params.put("CPF", order.customer().cpf());
        params.put("LOGRADOURO", order.customer().address());
        params.put("NUMERO", order.customer().number());
        params.put("BAIRRO", order.customer().district());
        params.put("EMAIL", order.customer().email());
        params.put("TELEFONE", order.customer().telephone());
        params.put("DATA_PEDIDO", order.date());
        params.put("TOTAL_PEDIDO", order.total());

        params.put("LOGO", logo.getFile().getAbsolutePath());
        return params;
    }

}
