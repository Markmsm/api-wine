package br.com.wine.apiwine.controller;

import br.com.wine.apiwine.data.model.Client;
import br.com.wine.apiwine.data.model.Wine;
import br.com.wine.apiwine.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public Client getClientWithUniqueHighestBuy(@PathVariable String year) {
        try {
            return service.getClientWithMaxBuyInYear(year);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        }
    }

    @GetMapping("/loyal-clients")
    public ArrayList<Client> getLoyalClients() {
        return service.getLoyalClients();
    }

    @GetMapping("/wine/{cpf}")
    public Wine getRecommendedWine(@PathVariable("cpf") String cpf) {
        return service.getRecommendedWine(cpf);
    }
}
