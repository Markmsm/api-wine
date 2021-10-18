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

//        Purchase[] purchases = result.getBody();
//
//        for (int i = 0; i < purchases.length; i++) {
//            if (purchases[i].getCliente().length() != 14) {
//                purchases[i].setCliente(purchases[i].getCliente().substring(1, 15));
//            }
//        }

        return Arrays.asList(result.getBody());
    }
}
