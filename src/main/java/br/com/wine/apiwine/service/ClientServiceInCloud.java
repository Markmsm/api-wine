package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.Client;
import br.com.wine.apiwine.data.model.Purchase;
import br.com.wine.apiwine.data.model.TempClient;
import br.com.wine.apiwine.data.model.Wine;
import br.com.wine.apiwine.repository.ClientInCloudRepository;

import java.util.*;

public class ClientServiceInCloud {

    private ClientInCloudRepository clientRepository;
    private PurchaseServiceInCloud purchaseServiceInCloud;

    public ClientServiceInCloud(ClientInCloudRepository clientRepository, PurchaseServiceInCloud purchaseServiceInCloud) {
        this.clientRepository = clientRepository;
        this.purchaseServiceInCloud = purchaseServiceInCloud;
    }

    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    public List<Client> getClientsSortedByMaxSpent() {
        List<Purchase> purchases = purchaseServiceInCloud.getAll();
        ArrayList<TempClient> clientsWithExpended = new ArrayList<>();
        List<Client> clients = getAll();
        List<Client> sortedClients = new ArrayList<>();

        for (Client client : clients) {
            clientsWithExpended.add(new TempClient(client, 0.0));
        }

        for (Purchase purchase : purchases) {
            for (TempClient tempClient : clientsWithExpended) {
                if (isPurchaseFromClient(tempClient.getClient().getCpf(), purchase.getCliente())) {
                    tempClient.setTotalExpended(tempClient.getTotalExpended() + purchase.getValorTotal());
                    break;
                }
            }
        }

        clientsWithExpended.sort(Comparator.comparing(TempClient::getTotalExpended).reversed());

        for (TempClient tempClient : clientsWithExpended) {
            sortedClients.add(tempClient.getClient());
        }

        return sortedClients;
    }

    public Client getClientWithMaxBuyInYear(String year) {
        List<Purchase> purchases = purchaseServiceInCloud.getAll();
        List<Client> clients = getAll();
        Client clientWithHighestBuy = null;

        purchases.sort(Comparator.comparing(Purchase::getValorTotal).reversed());

        for (Purchase purchase : purchases) {
            if (!purchase.getData().contains(year)) {
                continue;
            }

            for (Client client : clients) {
                if (isPurchaseFromClient(client.getCpf(), purchase.getCliente())) {
                    clientWithHighestBuy = client;
                    break;
                }
            }

            if (clientWithHighestBuy != null) {
                break;
            }
        }

        return clientWithHighestBuy;
    }

    public ArrayList<Client> getLoyalClients() {
        List<Purchase> purchases = purchaseServiceInCloud.getAll();
        List<Client> clients = getAll();
        ArrayList<TempClient> loyalTempClients = new ArrayList<>();
        ArrayList<Client> loyalClients = new ArrayList<>();

        for (Client client : clients) {
            loyalTempClients.add(new TempClient(client, 0));
        }

        for (Purchase purchase : purchases) {
            for (TempClient tempClient : loyalTempClients) {
                if (isPurchaseFromClient(tempClient.getClient().getCpf(), purchase.getCliente())) {
                    tempClient.setBuyTimes(tempClient.getBuyTimes() + 1);
                    break;
                }
            }
        }

        loyalTempClients.removeIf(tempClient -> tempClient.getBuyTimes() < 3);
        loyalTempClients.sort(Comparator.comparing(TempClient::getBuyTimes).reversed());

        for (TempClient tempClient : loyalTempClients) {
            loyalClients.add(tempClient.getClient());
        }

        return loyalClients;
    }

    public Wine getRecommendedWine(String cpf) {
        ArrayList<Purchase> purchases = purchaseServiceInCloud.getClientPurchases(cpf);
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