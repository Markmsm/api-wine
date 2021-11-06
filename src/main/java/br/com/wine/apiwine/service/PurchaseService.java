package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.Purchase;
import br.com.wine.apiwine.repository.PurchaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseService {

    PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getAll() {
        return purchaseRepository.getAll();
    }

    public List<Purchase> getClientPurchases(String cpf) {
        List<Purchase> purchases = getAll();
        if (purchases.isEmpty()) {
            return purchases;
        }

        return purchases
                .stream()
                .filter(p -> formatCpf(cpf).equals(formatCpf(p.getCliente())))
                .collect(Collectors.toList());
    }

    private String formatCpf(String cpf) {
        if (cpf.length() != 14) {
            cpf = cpf.substring(1, 15);
        }
        return cpf.replaceAll("\\.|-", "");
    }

}
