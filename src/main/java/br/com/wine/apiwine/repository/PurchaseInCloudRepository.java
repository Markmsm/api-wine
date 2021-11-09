package br.com.wine.apiwine.repository;

import br.com.wine.apiwine.data.model.Purchase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class PurchaseInCloudRepository implements PurchaseRepository {


    public List<Purchase> getAll() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Purchase[]> result = restTemplate.getForEntity(
                "http://www.mocky.io/v2/598b16861100004905515ec7",
                Purchase[].class);

        return Arrays.asList(result.getBody());
    }
}
