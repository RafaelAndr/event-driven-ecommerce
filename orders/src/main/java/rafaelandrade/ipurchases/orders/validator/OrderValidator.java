package rafaelandrade.ipurchases.orders.validator;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rafaelandrade.ipurchases.orders.client.CustomerClient;
import rafaelandrade.ipurchases.orders.client.ProductClient;
import rafaelandrade.ipurchases.orders.client.representation.CustomerRepresentation;
import rafaelandrade.ipurchases.orders.client.representation.ProductRepresentation;
import rafaelandrade.ipurchases.orders.entity.Order;
import rafaelandrade.ipurchases.orders.entity.OrderItem;
import rafaelandrade.ipurchases.orders.exception.ValidateException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderValidator {
    private final ProductClient productClient;
    private final CustomerClient customerClient;

    public void validate(Order order) {
        Long customerCode = order.getCustomerCode();
        validateCustomer(customerCode);
        order.getItems().forEach(this::validateItem);
    }

    public void validateCustomer(Long customerCode){
        try {
            var response = customerClient.getCustomer(customerCode);
            CustomerRepresentation customer = response.getBody();
            assert customer != null;
            log.info("Customer of code {} founded: {}", customer.code(), customer.name());
        } catch (FeignException.NotFound e) {
            var message = String.format("Customer of code %d not found", customerCode);
            throw new ValidateException("customerCode", message);
        }
    }

    public void validateItem(OrderItem item){
        try {
            var response = productClient.getProduct(item.getProductCode());
            ProductRepresentation product = response.getBody();
            assert product != null;
            log.info("Product of code {} founded: {}", product.code(), product.name());
        } catch (FeignException.NotFound e){
            var message = String.format("Product of code %d not found", item.getProductCode());
            throw new ValidateException("productCode", message);
        }
    }
}
