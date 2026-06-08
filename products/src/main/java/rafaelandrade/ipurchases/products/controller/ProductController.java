package rafaelandrade.ipurchases.products.controller;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafaelandrade.ipurchases.products.entity.Product;
import rafaelandrade.ipurchases.products.service.ProductService;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product){
        service.save(product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("{code}")
    public ResponseEntity<Product> getProduct(@PathVariable("code") Long code){
        return service
                .getByCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build())
                ;
    }
}
