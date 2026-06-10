package rafaelandrade.ipurchases.orders.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rafaelandrade.ipurchases.orders.client.representation.ProductRepresentation;

@FeignClient(name = "products", url = "${ipurchases.orders.clients.products.url}")
public interface ProductClient {

    @GetMapping("{code}")
    ResponseEntity<ProductRepresentation> getProduct(@PathVariable("code") Long code);

}

