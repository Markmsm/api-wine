package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.*;
import br.com.wine.apiwine.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    static ClientRepository mockedClientRepository;
    static PurchaseService mockedPurchaseService;
    static ClientService clientService;
    static PurchaseCreator purchaseCreator;
    static ClientCreator clientCreator;

    @BeforeEach
    void setUp() {
        mockedClientRepository = mock(ClientRepository.class);
        mockedPurchaseService = mock(PurchaseService.class);
        clientService = new ClientService(mockedClientRepository, mockedPurchaseService);
        purchaseCreator = new PurchaseCreator();
        clientCreator = new ClientCreator();

        when(mockedClientRepository.getAll()).thenReturn(clientCreator.getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(purchaseCreator.getFakePurchases());
    }

    @Test
    void getAllShouldThrowExceptionIfNoClient() {
        when(mockedClientRepository.getAll()).thenReturn(clientCreator.getFakeClientsEmpty());

        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            List<Client> clients = clientService.getAll();
        });

        assertEquals("There is no client!", ex.getMessage());
    }

    @Test
    void getAllShouldReturnClients() {
        List<Client> expectedClients = clientCreator.getFakeClients();
        List<Client> clients = clientService.getAll();

        assertEquals(expectedClients.size(), clients.size());
        assertTrue(clients.containsAll(expectedClients));
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnNoClientIfNotExistClient() {
        when(mockedClientRepository.getAll()).thenReturn(clientCreator.getFakeClientsEmpty());
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        assertTrue(clients.isEmpty());
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnNoClientIfNotExistPurchase() {
        when(mockedPurchaseService.getAll()).thenReturn(purchaseCreator.getFakePurchasesEmpty());
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        assertTrue(clients.isEmpty());
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnClientsWithoutSpentInLastPosition() {
        List<Client> expectedClients = clientCreator.getFakeClients();
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        assertEquals(expectedClients.size(), clients.size());
        assertEquals(expectedClients.get(6).getId(), clients.get(5).getId());
        assertEquals(expectedClients.get(4).getId(), clients.get(6).getId());
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnSortedClientsByMaxSpent() {
        List<Client> expectedClients = clientCreator.getFakeClients();
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        assertEquals(expectedClients.size(), clients.size());
        assertEquals(expectedClients.get(1).getId(), clients.get(0).getId());
        assertEquals(expectedClients.get(5).getId(), clients.get(1).getId());
        assertEquals(expectedClients.get(0).getId(), clients.get(2).getId());
        assertEquals(expectedClients.get(2).getId(), clients.get(3).getId());
        assertEquals(expectedClients.get(3).getId(), clients.get(4).getId());
    }

    @Test
    void getClientWithMaxBuyInYearShouldThrowExceptionIfNotExistClient() {
        when(mockedClientRepository.getAll()).thenReturn(clientCreator.getFakeClientsEmpty());
        String year = "2016";

        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Client client = clientService.getClientWithMaxBuyInYear(year);
        });

        assertEquals("There are no client or purchase in" + year, ex.getMessage());
    }

    @Test
    void getClientWithMaxBuyInYearShouldThrowExceptionIfNotExistPurchase() {
        when(mockedPurchaseService.getAll()).thenReturn(purchaseCreator.getFakePurchasesEmpty());
        String year = "2016";

        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Client client = clientService.getClientWithMaxBuyInYear(year);
        });

        assertEquals("There are no client or purchase in" + year, ex.getMessage());
    }

    @Test
    void getClientWithMaxBuyInYearShouldThrowExceptionIfNotExistPurchaseInYear() {
        String year = "1994";

        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Client client = clientService.getClientWithMaxBuyInYear(year);
        });

        assertEquals("There are no client or purchase in" + year, ex.getMessage());
    }

    @Test
    void getClientWithMaxBuyInYearShouldReturnClientWithMaxBuyInYear() {
        int expectedClientId = clientCreator.getFakeClients().get(1).getId();
        int clientWithMaxBuyId = clientService.getClientWithMaxBuyInYear("2016").getId();

        assertEquals(expectedClientId, clientWithMaxBuyId);
    }

    @Test
    void getLoyalClientsShouldReturnNoClientIfNotExistClient() {
        when(mockedClientRepository.getAll()).thenReturn(clientCreator.getFakeClientsEmpty());
        List<Client> clients = clientService.getLoyalClients();

        assertTrue(clients.isEmpty());
    }

    @Test
    void getLoyalClientsShouldReturnNoClientIfNotExistPurchase() {
        when(mockedPurchaseService.getAll()).thenReturn(purchaseCreator.getFakePurchasesEmpty());
        List<Client> clients = clientService.getLoyalClients();

        assertTrue(clients.isEmpty());
    }

    @Test
    void getLoyalClientsShouldReturnLoyalClients() {
        List<Client> fakeClients = clientCreator.getFakeClients();
        List<Client> loyalClients = clientService.getLoyalClients();

        assertEquals(2, loyalClients.size());
        assertEquals(fakeClients.get(1).getId(), loyalClients.get(0).getId());
        assertEquals(fakeClients.get(2).getId(), loyalClients.get(1).getId());
    }

    @Test
    void getRecommendedWineShouldThrowExceptionIfClientHasNoPurchase() {
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Wine wine = clientService.getRecommendedWine("000.000.000-08");
        });

        assertEquals("This client haven't purchase yet!", ex.getMessage());
    }

    @Test
    void getRecommendedWineShouldReturnRecommendedWine() {
        String fakeCPF = "000.000.000-02";
        when(mockedPurchaseService.getClientPurchases(fakeCPF)).thenReturn(purchaseCreator.getPurchasesOfClient(fakeCPF));

        Wine wine = clientService.getRecommendedWine(fakeCPF);
        Wine expectedWine = new WineCreator().getFakeWines().get(0);

        assertEquals(expectedWine.getCodigo(), wine.getCodigo());
    }
}
