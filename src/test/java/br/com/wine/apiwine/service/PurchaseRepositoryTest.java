package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.Purchase;
import br.com.wine.apiwine.data.model.PurchaseCreator;
import br.com.wine.apiwine.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PurchaseRepositoryTest {

    @Test
    void shouldReturnPurchasesFromClient() {
        PurchaseRepository mockedPurchaseRepository = mock(PurchaseRepository.class);
        PurchaseService purchaseService = new PurchaseService(mockedPurchaseRepository);
        PurchaseCreator purchaseCreator = new PurchaseCreator();
        when(purchaseService.getAll()).thenReturn(purchaseCreator.getFakePurchases());

        String clientCpf = "000.000.000.01";
        ArrayList<Purchase> purchases = purchaseService.getClientPurchases(clientCpf);

        assertEquals(2, purchases.size());
        assertEquals(clientCpf, purchases.get(0).getCliente());
        assertEquals(clientCpf, purchases.get(1).getCliente());
    }
}
