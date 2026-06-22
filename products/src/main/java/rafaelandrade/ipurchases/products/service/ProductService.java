package rafaelandrade.ipurchases.products.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rafaelandrade.ipurchases.products.entity.Product;
import rafaelandrade.ipurchases.products.repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public void save(Product product){
        repository.save(product);
    }

    public Optional<Product> getByCode(Long code){
        return repository.findById(code);
    }
}
