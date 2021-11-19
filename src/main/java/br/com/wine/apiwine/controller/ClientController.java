package br.com.wine.apiwine.controller;

import br.com.wine.apiwine.data.model.Client;
import br.com.wine.apiwine.data.model.Wine;
import br.com.wine.apiwine.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/clients/v1")
public class ClientController {

    private ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/max-spent")
    public List<Client> getClientsSortedByMaxSpent() {
        return service.getClientsSortedByMaxSpent();
    }

    @GetMapping("/max-purchase/{year}")
    public Client getClientWithUniqueHighestBuyInYear(@PathVariable String year) {
        try {
            return service.getClientWithMaxBuyInYear(year);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no client or purchase in " + year);
        }
    }

    @GetMapping("/loyal-clients")
    public List<Client> getLoyalClients() {
        return service.getLoyalClients();
    }

    @GetMapping("/wine/{cpf}")
    public Wine getRecommendedWine(@PathVariable("cpf") String cpf) {
        try {
            return service.getRecommendedWine(cpf);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This client haven't purchase yet!");
        }
    }
}
