package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.Purchase;
import br.com.wine.apiwine.repository.PurchaseInCloudRepository;

import java.util.ArrayList;
import java.util.List;

public class PurchaseServiceInCloud {

    PurchaseInCloudRepository purchaseInCloudRepository;

    public PurchaseServiceInCloud(PurchaseInCloudRepository purchaseInCloudRepository) {
        this.purchaseInCloudRepository = purchaseInCloudRepository;
    }

    public List<Purchase> getAll() {
        return purchaseInCloudRepository.getAll();
    }

    public ArrayList<Purchase> getClientPurchases(String cpf) {
        List<Purchase> purchases = getAll();
        ArrayList<Purchase> clientPurchases = new ArrayList<>();

        for (Purchase purchase : purchases) {
            if (formatCpf(cpf).equals(formatCpf(purchase.getCliente()))) {
                clientPurchases.add(purchase);
            }
        }

        return clientPurchases;
    }

    private String formatCpf(String cpf) {
        if (cpf.length() != 14) {
            cpf = cpf.substring(1, 15);
        }
        return cpf.replaceAll("\\.|-", "");
    }

}
