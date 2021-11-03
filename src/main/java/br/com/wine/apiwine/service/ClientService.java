package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.*;
import br.com.wine.apiwine.repository.ClientRepository;

import java.util.*;
import java.util.stream.Collectors;

public class ClientService {

    // TODO: - implementar repository utilizando arquivo
    //       - pegar dados de um .csv para clientes e de um .json ou .csv para compras (preferencia para .csv)
    private ClientRepository clientRepository;
    private PurchaseService purchaseService;

    public ClientService(ClientRepository clientRepository, PurchaseService purchaseService) {
        this.clientRepository = clientRepository;
        this.purchaseService = purchaseService;
    }

    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    public List<Client> getClientsSortedByMaxSpent() {
        List<Purchase> purchases = purchaseService.getAll();
        List<Client> clients = getAll();

        if (clients.isEmpty() || purchases.isEmpty()) {
            clients.clear();
            return clients;
        }

        List<String> sortedCPFClientsByTotalSpent = purchases
                .stream()
                .collect(Collectors.groupingBy(Purchase::getCliente,
                        Collectors.summingDouble(Purchase::getValorTotal)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(this::formatCpf)
                .collect(Collectors.toList());

        clients.forEach(c -> c.setCpf(formatCpf(c.getCpf())));
        clients.sort(Comparator.comparing(c -> sortedCPFClientsByTotalSpent.indexOf(c.getCpf())));
        Collections.reverse(clients);

        return clients;
    }

    public Client getClientWithMaxBuyInYear(String year) {
        List<Purchase> purchases = purchaseService.getAll();
        List<Client> clients = getAll();

        if (purchases.isEmpty()) {
            throw new NoSuchElementException("There is no purchase!");
        }
        if (clients.isEmpty()) {
            throw new NoSuchElementException("There is no client!");
        }

        try {
            Optional<Purchase> highestPurchase = purchases
                    .stream()
                    .filter(purchase -> purchase.getData().contains(year))
                    .max(Comparator.comparing(Purchase::getValorTotal));

            Optional<Client> clientWithHighestBuy = clients
                    .stream()
                    .filter(client -> isPurchaseFromClient(client.getCpf(), highestPurchase.get().getCliente()))
                    .findFirst();

            return clientWithHighestBuy.get();
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Are not purchases this year!");
        }
    }

    public List<Client> getLoyalClients() {
        List<Purchase> purchases = purchaseService.getAll();
        List<Client> clients = getAll();

        if (clients.isEmpty() || purchases.isEmpty()) {
            clients.clear();
            return clients;
        }

        List<String> sortedCPFClientsByNumOfPurchases = purchases
                .stream()
                .collect(Collectors.groupingBy(Purchase::getCliente,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .filter(p -> p.getValue() > 3)
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(this::formatCpf)
                .collect(Collectors.toList());

        clients.forEach(c -> c.setCpf(formatCpf(c.getCpf())));

        List<Client> loyalClients = clients
                .stream()
                .filter(c -> sortedCPFClientsByNumOfPurchases.contains(c.getCpf()))
                .sorted(Comparator.comparing(c -> sortedCPFClientsByNumOfPurchases.indexOf(c.getCpf())))
                .collect(Collectors.toList());

        //testar se não houver cliente no indice 2 mas houver no 3

        Collections.reverse(loyalClients);

        return loyalClients;
    }

    public Optional<Wine> getRecommendedWine(String cpf) {
        List<Purchase> purchasesOfClient = purchaseService.getClientPurchases(cpf);
        Map<Wine, Integer> wines = new HashMap<>();

        if (purchasesOfClient.isEmpty()) {
            throw new NoSuchElementException("This client haven't purchase yet!");
        }

        purchasesOfClient
                .forEach(p -> p.getItens()
                        .forEach(w -> wines.put(w, wines.containsKey(w) ? (wines.get(w) + 1) : 1)));

        return wines
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .stream()
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private boolean isPurchaseFromClient(String clientCpf, String purchaseCpf) {
        return formatCpf(clientCpf).equals(formatCpf(purchaseCpf));
    }

    private String formatCpf(String cpf) {
        if (cpf.length() != 14) {
            cpf = cpf.substring(1, 15);
        }
        return cpf.replaceAll("\\.|-", "");
    }
}