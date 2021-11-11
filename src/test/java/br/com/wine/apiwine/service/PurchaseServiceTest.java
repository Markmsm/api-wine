package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.Purchase;
import br.com.wine.apiwine.data.model.PurchaseCreator;
import br.com.wine.apiwine.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static br.com.wine.apiwine.data.model.PurchaseCreator.getFakePurchases;
import static br.com.wine.apiwine.data.model.PurchaseCreator.getFakePurchasesEmpty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PurchaseServiceTest {

    static PurchaseRepository mockedPurchaseRepository;
    static PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        mockedPurchaseRepository = mock(PurchaseRepository.class);
        purchaseService = new PurchaseService(mockedPurchaseRepository);
    }

    @Test
    void getAllShouldThrowExceptionIfNoPurchaseExist() {
        //Given:
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchasesEmpty());

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            List<Purchase> purchases = purchaseService.getAll();
        });

        //Then:
        assertEquals("There is no purchase!", ex.getMessage());
    }

    @Test
    void getAllShouldReturnAllPurchases() {
        //Given:
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());
        List<Purchase> expectedPurchases = getFakePurchases();

        //When:
        List<Purchase> purchases = purchaseService.getAll();

        //Then:
        assertEquals(expectedPurchases.size(), purchases.size());
        assertTrue(purchases.containsAll(expectedPurchases));
    }

    @Test
    void getClientPurchasesShouldReturnEmptyListIfNoPurchaseExist() {
        //Given:
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchasesEmpty());

        //When:
        List<Purchase> purchases = purchaseService.getClientPurchases("000.000.000-08");

        //Then:
        assertTrue(purchases.isEmpty());
    }

    @Test
    void getClientPurchasesShouldReturnEmptyListIfNoPurchaseExistFromClient() {
        //Given:
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        //When:
        List<Purchase> purchases = purchaseService.getClientPurchases("000.000.000-08");

        //Then:
        assertTrue(purchases.isEmpty());
    }

    @Test
    void getClientPurchasesShouldReturnPurchasesFromClient() {
        //Given:
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());
        String clientCpf = "000.000.000.01";

        //When:
        List<Purchase> purchases = purchaseService.getClientPurchases(clientCpf);

        //Then:
        assertEquals(2, purchases.size());
        assertEquals(clientCpf, purchases.get(0).getCliente());
        assertEquals(clientCpf, purchases.get(1).getCliente());
    }
}
