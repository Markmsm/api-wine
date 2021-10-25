package br.com.wine.apiwine.configuration;

import br.com.wine.apiwine.repository.*;
import br.com.wine.apiwine.service.ClientService;
import br.com.wine.apiwine.service.PurchaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WineConfiguration {

    @Bean
    ClientService clientServiceInCloud(ClientRepository clientRepository,
                                       PurchaseService purchaseService) {
        return new ClientService(clientRepository, purchaseService);
    }

    @Bean
    ClientRepository clientRepository() {
        return new ClientInFileRepository();
    }

    @Bean
    PurchaseService purchaseService(PurchaseRepository purchaseRepository) {
        return new PurchaseService(purchaseRepository);
    }

    @Bean
    PurchaseInCloudRepository purchaseInCloudRepository() {
        return new PurchaseInCloudRepository();
    }
}
