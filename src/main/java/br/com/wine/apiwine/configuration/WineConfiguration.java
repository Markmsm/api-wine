package br.com.wine.apiwine.configuration;

import br.com.wine.apiwine.repository.*;
import br.com.wine.apiwine.service.ClientService;
import br.com.wine.apiwine.service.PurchaseService;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

@Configuration
public class WineConfiguration {

    @Bean
    ClientService clientServiceInCloud(ClientRepository clientRepository,
                                       PurchaseService purchaseService) {
        return new ClientService(clientRepository, purchaseService);
    }

    @Bean
    ClientRepository clientRepository() throws FileNotFoundException {
        return new ClientInFileRepository(
                new Scanner(new File("/home/ubots/Learnspace/api-wine/src/main/files/clients.csv"))
        );
    }

    @Bean
    PurchaseService purchaseService(PurchaseRepository purchaseRepository) {
        return new PurchaseService(purchaseRepository);
    }

    @Bean
    PurchaseRepository purchaseRepository() throws FileNotFoundException {
        return new PurchaseInFileRepository(
                new FileReader("/home/ubots/Learnspace/api-wine/src/main/files/purchases.json"),
                new Gson()
        );
    }
}
