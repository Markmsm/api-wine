package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.Client;
import br.com.wine.apiwine.data.model.Purchase;
import br.com.wine.apiwine.data.model.TempClient;
import br.com.wine.apiwine.data.model.Wine;
import br.com.wine.apiwine.repository.ClientRepository;

import java.util.*;

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
        ArrayList<TempClient> clientsWithExpended = new ArrayList<>();//nomenclatura da classe TempClient
        List<Client> clients = getAll();
        List<Client> sortedClients = new ArrayList<>();

        if (clients.isEmpty() || purchases.isEmpty()) {
            return sortedClients;
        }

        clients.forEach(client -> clientsWithExpended.add(new TempClient(client, 0.0)));

        purchases.forEach(purchase -> clientsWithExpended.forEach(tempClient -> {
            if (isPurchaseFromClient(tempClient.getClient().getCpf(), purchase.getCliente())) {
                tempClient.setTotalExpended(tempClient.getTotalExpended() + purchase.getValorTotal());
            }
        }));

        clientsWithExpended.sort(Comparator.comparing(TempClient::getTotalExpended).reversed());
        clientsWithExpended.forEach(tempClient -> sortedClients.add(tempClient.getClient()));

        return sortedClients;
    }

    public Client getClientWithMaxBuyInYear(String year) throws Exception {
        List<Purchase> purchases = purchaseService.getAll();
        List<Client> clients = getAll();
        Optional<Client> clientWithHighestBuy = null;
        Optional<Purchase> highestPurchase;

        if (purchases.isEmpty()) {
            throw new NoSuchElementException("There is no purchase!");
        }
        if (clients.isEmpty()) {
            throw new NoSuchElementException("There is no client!");
        }

        try {
            highestPurchase = purchases
                    .stream()
                    .filter(purchase -> purchase.getData().contains(year))
                    .max(Comparator.comparing(Purchase::getValorTotal));

            clientWithHighestBuy = clients
                    .stream()
                    .filter(client -> isPurchaseFromClient(client.getCpf(), highestPurchase.get().getCliente()))
                    .findFirst();

            return clientWithHighestBuy.get();
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Are not purchases this year!");
        }
    }

    public ArrayList<Client> getLoyalClients() {
        List<Purchase> purchases = purchaseService.getAll();
        List<Client> clients = getAll();
        ArrayList<TempClient> loyalTempClients = new ArrayList<>();
        ArrayList<Client> loyalClients = new ArrayList<>();

        clients.forEach(client -> loyalTempClients.add(new TempClient(client, 0)));

        purchases.forEach(purchase -> {
            loyalTempClients.forEach(tempClient -> {
                if (isPurchaseFromClient(tempClient.getClient().getCpf(), purchase.getCliente())) {
                    tempClient.setBuyTimes(tempClient.getBuyTimes() + 1);
                }
            });
        });

        loyalTempClients.removeIf(tempClient -> tempClient.getBuyTimes() < 3);
        loyalTempClients
                .stream()
                .sorted(Comparator.comparing(TempClient::getBuyTimes).reversed())
                .forEach(tempClient -> loyalClients.add(tempClient.getClient()));
//        loyalTempClients.sort(Comparator.comparing(TempClient::getBuyTimes).reversed());
//        loyalTempClients.forEach(tempClient -> loyalClients.add(tempClient.getClient()));

        return loyalClients;
    }

    public Wine getRecommendedWine(String cpf) {
        ArrayList<Purchase> purchases = purchaseService.getClientPurchases(cpf);
        Map<Wine, Integer> wines = new HashMap<>();
        Wine recommendedWine = new Wine();
        int countWine = 0;

        for (Purchase purchase : purchases) {
            for (Wine wine : purchase.getItens()) {
                if (wines.containsKey(wine)) {
                    wines.put(wine, wines.get(wine) + 1);
                } else {
                    wines.put(wine, 1);
                }
            }
        }

        for (Map.Entry<Wine, Integer> wine : wines.entrySet()) {
            if (wine.getValue() > countWine) {
                countWine = wine.getValue();
                recommendedWine = wine.getKey();
            }
        }

        return recommendedWine;
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