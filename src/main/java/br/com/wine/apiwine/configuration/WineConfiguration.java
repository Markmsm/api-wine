package br.com.wine.apiwine.configuration;

import br.com.wine.apiwine.repository.ClientInCloudRepository;
import br.com.wine.apiwine.repository.ClientRepository;
import br.com.wine.apiwine.repository.PurchaseInCloudRepository;
import br.com.wine.apiwine.service.ClientService;
import br.com.wine.apiwine.service.PurchaseServiceInCloud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WineConfiguration {

    @Bean
    ClientService clientServiceInCloud(ClientRepository clientRepository,
                                       PurchaseServiceInCloud purchaseServiceInCloud) {
        return new ClientService(clientRepository, purchaseServiceInCloud);
    }

    @Bean
    ClientInCloudRepository clientInCloudRepository() {
        return new ClientInCloudRepository();
    }

    @Bean
    PurchaseServiceInCloud purchaseService(PurchaseInCloudRepository purchaseInCloudRepository) {
        return new PurchaseServiceInCloud(purchaseInCloudRepository);
    }

    @Bean
    PurchaseInCloudRepository purchaseInCloudRepository() {
        return new PurchaseInCloudRepository();
    }
}
