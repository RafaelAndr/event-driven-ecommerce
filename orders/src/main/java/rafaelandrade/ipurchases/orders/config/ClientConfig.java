package rafaelandrade.ipurchases.orders.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "rafaelandrade.ipurchases.orders.client")
public class ClientConfig {
}
