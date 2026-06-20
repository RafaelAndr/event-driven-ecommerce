package rafaelandrade.ipurchases.orders.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rafaelandrade.ipurchases.orders.entity.enums.OrderStatus;
import rafaelandrade.ipurchases.orders.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusService {

    private final OrderRepository repository;

    @Transactional
    public void updateStatus(Long code, OrderStatus status, String urlNf, String trackingCode){

        repository.findById(code).ifPresent(
                order -> {
                    order.setStatus(status);
                    if (urlNf != null){
                        order.setUrlNf(urlNf);
                    }
                    if (trackingCode != null){
                        order.setTrackingCode(trackingCode);
                    }
                }
        );
    }
}
