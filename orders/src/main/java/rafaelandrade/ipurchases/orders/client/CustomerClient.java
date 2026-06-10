package rafaelandrade.ipurchases.orders.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rafaelandrade.ipurchases.orders.client.representation.CustomerRepresentation;

@FeignClient(name = "customers", url = "${ipurchases.orders.clients.customers.url}")
public interface CustomerClient {

    @GetMapping("{code}")
    ResponseEntity<CustomerRepresentation> getCustomer(@PathVariable("code") Long code);
}
