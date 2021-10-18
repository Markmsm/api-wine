package br.com.wine.apiwine.configuration;

import br.com.wine.apiwine.repository.ClientInCloudRepository;
import br.com.wine.apiwine.repository.ClientRepository;
import br.com.wine.apiwine.repository.PurchaseInCloudRepository;
import br.com.wine.apiwine.repository.PurchaseRepository;
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
    ClientInCloudRepository clientInCloudRepository() {
        return new ClientInCloudRepository();
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
