package br.com.wine.apiwine.configuration;

import br.com.wine.apiwine.repository.ClientInCloudRepository;
import br.com.wine.apiwine.repository.PurchaseInCloudRepository;
import br.com.wine.apiwine.service.ClientServiceInCloud;
import br.com.wine.apiwine.service.PurchaseServiceInCloud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WineConfiguration {

    @Bean
    ClientServiceInCloud clientServiceInCloud(ClientInCloudRepository clientInCloudRepository,
                                              PurchaseServiceInCloud purchaseServiceInCloud) {
        return new ClientServiceInCloud(clientInCloudRepository, purchaseServiceInCloud);
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
