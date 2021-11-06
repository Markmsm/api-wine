package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.Purchase;
import br.com.wine.apiwine.data.model.PurchaseCreator;
import br.com.wine.apiwine.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PurchaseServiceTest {

    static PurchaseRepository mockedPurchaseRepository;
    static PurchaseService purchaseService;
    static PurchaseCreator purchaseCreator;

    @BeforeEach
    void setUp() {
        mockedPurchaseRepository = mock(PurchaseRepository.class);
        purchaseService = new PurchaseService(mockedPurchaseRepository);
        purchaseCreator = new PurchaseCreator();

        when(mockedPurchaseRepository.getAll()).thenReturn(purchaseCreator.getFakePurchases());
    }

    @Test
    void getAllShouldThrowExceptionIfNoPurchaseExist() {
        when(mockedPurchaseRepository.getAll()).thenReturn(purchaseCreator.getFakePurchasesEmpty());
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            List<Purchase> purchases = purchaseService.getAll();
        });

        assertEquals("There is no purchase!", ex.getMessage());
    }

    @Test
    void getAllShouldReturnAllPurchases() {
        List<Purchase> expectedPurchases = purchaseCreator.getFakePurchases();
        List<Purchase> purchases = purchaseService.getAll();

        assertEquals(expectedPurchases.size(), purchases.size());
        assertTrue(purchases.containsAll(expectedPurchases));
    }

    @Test
    void getClientPurchasesShouldReturnEmptyListIfNoPurchaseExist() {
        when(mockedPurchaseRepository.getAll()).thenReturn(purchaseCreator.getFakePurchasesEmpty());

        List<Purchase> purchases = purchaseService.getClientPurchases("000.000.000-08");

        assertTrue(purchases.isEmpty());
    }

    @Test
    void getClientPurchasesShouldReturnEmptyListIfNoPurchaseExistFromClient() {
        List<Purchase> purchases = purchaseService.getClientPurchases("000.000.000-08");

        assertTrue(purchases.isEmpty());
    }

    @Test
    void getClientPurchasesShouldReturnPurchasesFromClient() {
        String clientCpf = "000.000.000.01";
        List<Purchase> purchases = purchaseService.getClientPurchases(clientCpf);

        assertEquals(2, purchases.size());
        assertEquals(clientCpf, purchases.get(0).getCliente());
        assertEquals(clientCpf, purchases.get(1).getCliente());
    }
}
