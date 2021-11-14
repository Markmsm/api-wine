package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.Purchase;
import br.com.wine.apiwine.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static br.com.wine.apiwine.data.model.PurchaseCreator.getFakePurchases;
import static br.com.wine.apiwine.data.model.PurchaseCreator.getFakePurchasesEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertThat(ex.getMessage(), is("There is no purchase!"));
    }

    @Test
    void getAllShouldReturnAllPurchases() {
        //Given:
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());
        List<Purchase> expectedPurchases = getFakePurchases();

        //When:
        List<Purchase> purchases = purchaseService.getAll();

        //Then:
        assertThat(purchases, is(expectedPurchases));
    }

    @Test
    void getClientPurchasesShouldReturnEmptyListIfNoPurchaseExist() {
        //Given:
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchasesEmpty());

        //When:
        List<Purchase> purchases = purchaseService.getClientPurchases("000.000.000-08");

        //Then:
        assertThat(purchases, empty());
//        assertTrue(purchases.isEmpty());
    }

    @Test
    void getClientPurchasesShouldReturnEmptyListIfNoPurchaseExistFromClient() {
        //Given:
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        //When:
        List<Purchase> purchases = purchaseService.getClientPurchases("000.000.000-08");

        //Then:
        assertThat(purchases, empty());
//        assertTrue(purchases.isEmpty());
    }

    @Test
    void getClientPurchasesShouldReturnPurchasesFromClient() {
        //Given:
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());
        String clientCpf = "00000000001";

        //When:
        List<Purchase> purchases = purchaseService.getClientPurchases(clientCpf);

        //Then:
        assertThat(purchases.size(), is(2));
        assertThat(purchases.get(0).getCliente(), is(clientCpf));
        assertThat(purchases.get(1).getCliente(), is(clientCpf));
    }
}
