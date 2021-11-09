package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.*;
import br.com.wine.apiwine.repository.ClientRepository;

import java.util.*;
import java.util.stream.Collectors;

public class ClientService {

    private ClientRepository clientRepository;
    private PurchaseService purchaseService;

    public ClientService(ClientRepository clientRepository, PurchaseService purchaseService) {
        this.clientRepository = clientRepository;
        this.purchaseService = purchaseService;
    }

    public List<Client> getAll() {
        List<Client> clients = clientRepository.getAll();

        if (clients.isEmpty()) throw new NoSuchElementException("There is no client!");

        return clients;
    }

    public List<Client> getClientsSortedByMaxSpent() {
        try {
            List<Purchase> purchases = purchaseService.getAll();
            List<Client> clients = getAll();

            if (purchases.isEmpty()) return new ArrayList<>();

            //dúvida = melhor ter a lista purchases e validar se ela está vazia, ou ter a lista declarativa
            // sortedCPFClientsByTotalSpent, trabalhar nela e depois validar ela?

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
        } catch (NoSuchElementException ex) {
            return new ArrayList<>();
        }
    }

    public Client getClientWithMaxBuyInYear(String year) {
        try {
            Optional<Purchase> highestPurchase = purchaseService.getAll()
                    .stream()
                    .filter(purchase -> purchase.getData().contains(year))
                    .max(Comparator.comparing(Purchase::getValorTotal));

            Optional<Client> clientWithHighestBuy = getAll()
                    .stream()
                    .filter(client -> isPurchaseFromClient(client.getCpf(), highestPurchase.get().getCliente()))
                    .findFirst();

            return clientWithHighestBuy.get();
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("There are no client or purchase in" + year);
        }
    }

    public List<Client> getLoyalClients() {
        try {
            List<String> sortedCPFClientsByNumOfPurchases = purchaseService.getAll()
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

            List<Client> clients = getAll();
            clients.forEach(c -> c.setCpf(formatCpf(c.getCpf())));

            List<Client> loyalClients = clients
                    .stream()
                    .filter(c -> sortedCPFClientsByNumOfPurchases.contains(c.getCpf()))
                    .sorted(Comparator.comparing(c -> sortedCPFClientsByNumOfPurchases.indexOf(c.getCpf())))
                    .collect(Collectors.toList());

            Collections.reverse(loyalClients);

            return loyalClients;
        } catch (NoSuchElementException ex) {
            return new ArrayList<>();
        }
    }

    public Wine getRecommendedWine(String cpf) {
        List<Purchase> purchasesOfClient = purchaseService.getClientPurchases(cpf);
        Map<Wine, Integer> wines = new HashMap<>();

        if (purchasesOfClient.isEmpty()) throw new NoSuchElementException("This client haven't purchase yet!");

        purchasesOfClient
                .forEach(p -> p.getItens()
                        .forEach(w -> wines.put(w, wines.containsKey(w) ? (wines.get(w) + 1) : 1)));

        return wines
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .stream()
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
    }

    private boolean isPurchaseFromClient(String clientCpf, String purchaseCpf) {
        return formatCpf(clientCpf).equals(formatCpf(purchaseCpf));
    }

    private String formatCpf(String cpf) {
        if (cpf.length() != 14) cpf = cpf.substring(cpf.length() - 14);

        return cpf.replaceAll("\\.|-", "");
    }
}