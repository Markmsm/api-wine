package br.com.wine.apiwine.repository;

import br.com.wine.apiwine.data.model.Purchase;

import java.util.List;

public interface PurchaseRepository {

    List<Purchase> getAll();
}
