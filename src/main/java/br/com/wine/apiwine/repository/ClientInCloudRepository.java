package br.com.wine.apiwine.repository;

import br.com.wine.apiwine.data.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ClientInCloudRepository {

    public List<Client> getAll() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Client[]> result = restTemplate.getForEntity(
                "http://www.mocky.io/v2/598b16291100004705515ec5",
                Client[].class);

        return Arrays.asList(result.getBody());
    }
}
