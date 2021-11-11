package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.Purchase;
import br.com.wine.apiwine.repository.PurchaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static br.com.wine.apiwine.service.CpfFormatter.formatCpf;

public class PurchaseService {

    PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getAll() {
        List<Purchase> purchases = purchaseRepository.getAll();

        if (purchases.isEmpty()) throw new NoSuchElementException("There is no purchase!");

        purchases.forEach(p -> p.setCliente(formatCpf(p.getCliente())));

        return purchases;
    }

    public List<Purchase> getClientPurchases(String cpf) {
        try {
            return getAll()
                    .stream()
                    .filter(p -> cpf.equals(p.getCliente()))
                    .collect(Collectors.toList());
        } catch (NoSuchElementException ex) {
            return new ArrayList<>();
        }
    }

}
