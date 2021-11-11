package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.*;
import br.com.wine.apiwine.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static br.com.wine.apiwine.data.model.ClientCreator.*;
import static br.com.wine.apiwine.data.model.PurchaseCreator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    static ClientRepository mockedClientRepository;
    static PurchaseService mockedPurchaseService;
    static ClientService clientService;

    @BeforeAll
    static void setUp() {
        mockedClientRepository = mock(ClientRepository.class);
        mockedPurchaseService = mock(PurchaseService.class);
        clientService = new ClientService(mockedClientRepository, mockedPurchaseService);
    }

    @Test
    void getAllShouldThrowExceptionIfNoClient() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClientsEmpty());

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            List<Client> clients = clientService.getAll();
        });

        //Then:
        assertEquals("There is no client!", ex.getMessage());
    }

    @Test
    void getAllShouldReturnClients() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        List<Client> expectedClients = getFakeClients();

        //When:
        List<Client> clients = clientService.getAll();

        //Then:
        assertEquals(expectedClients.size(), clients.size());
        assertTrue(clients.containsAll(expectedClients));
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnNoClientIfNotExistClient() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClientsEmpty());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());

        //When:
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        //Then:
        assertTrue(clients.isEmpty());
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnNoClientIfNotExistPurchase() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchasesEmpty());

        //When:
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        //Then:
        assertTrue(clients.isEmpty());
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnClientsWithoutSpentInLastPosition() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());
        List<Client> expectedClients = getFakeClients();

        //When:
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        //Then:
        assertEquals(expectedClients.size(), clients.size());
        assertEquals(expectedClients.get(6).getId(), clients.get(5).getId());
        assertEquals(expectedClients.get(4).getId(), clients.get(6).getId());
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnSortedClientsByMaxSpent() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());
        List<Client> expectedClients = getFakeClients();

        //When:
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        //Then:
        assertEquals(expectedClients.size(), clients.size());
        assertEquals(expectedClients.get(1).getId(), clients.get(0).getId());
        assertEquals(expectedClients.get(5).getId(), clients.get(1).getId());
        assertEquals(expectedClients.get(0).getId(), clients.get(2).getId());
        assertEquals(expectedClients.get(2).getId(), clients.get(3).getId());
        assertEquals(expectedClients.get(3).getId(), clients.get(4).getId());
    }

    @Test
    void getClientWithMaxBuyInYearShouldThrowExceptionIfNotExistClient() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClientsEmpty());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());
        String year = "2016";

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Client client = clientService.getClientWithMaxBuyInYear(year);
        });

        //Then:
        assertEquals("There are no client or purchase in" + year, ex.getMessage());
    }

    @Test
    void getClientWithMaxBuyInYearShouldThrowExceptionIfNotExistPurchase() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchasesEmpty());
        String year = "2016";

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Client client = clientService.getClientWithMaxBuyInYear(year);
        });

        //Then:
        assertEquals("There are no client or purchase in" + year, ex.getMessage());
    }

    @Test
    void getClientWithMaxBuyInYearShouldThrowExceptionIfNotExistPurchaseInYear() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());
        String year = "1994";

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Client client = clientService.getClientWithMaxBuyInYear(year);
        });

        //Then:
        assertEquals("There are no client or purchase in" + year, ex.getMessage());
    }

    @Test
    void getClientWithMaxBuyInYearShouldReturnClientWithMaxBuyInYear() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());
        int expectedClientId = getFakeClients().get(1).getId();

        //When:
        int clientWithMaxBuyId = clientService.getClientWithMaxBuyInYear("2016").getId();

        //Then:
        assertEquals(expectedClientId, clientWithMaxBuyId);
    }

    @Test
    void getLoyalClientsShouldReturnNoClientIfNotExistClient() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClientsEmpty());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());

        //When:
        List<Client> clients = clientService.getLoyalClients();

        //Then:
        assertTrue(clients.isEmpty());
    }

    @Test
    void getLoyalClientsShouldReturnNoClientIfNotExistPurchase() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchasesEmpty());

        //When:
        List<Client> clients = clientService.getLoyalClients();

        //Then:
        assertTrue(clients.isEmpty());
    }

    @Test
    void getLoyalClientsShouldReturnLoyalClients() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());
        List<Client> expectedClients = getFakeClients();

        //When:
        List<Client> loyalClients = clientService.getLoyalClients();

        //Then:
        assertEquals(2, loyalClients.size());
        assertEquals(expectedClients.get(1).getId(), loyalClients.get(0).getId());
        assertEquals(expectedClients.get(2).getId(), loyalClients.get(1).getId());
    }

    @Test
    void getRecommendedWineShouldThrowExceptionIfClientHasNoPurchase() {
        //Given:
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Wine wine = clientService.getRecommendedWine("000.000.000-08");
        });

        //Then:
        assertEquals("This client haven't purchase yet!", ex.getMessage());
    }

    @Test
    void getRecommendedWineShouldReturnRecommendedWine() {
        //Given:
        String fakeCPF = "000.000.000-02";
        Wine expectedWine = new WineCreator().getFakeWines().get(0);
        when(mockedPurchaseService.getClientPurchases(fakeCPF)).thenReturn(getPurchasesOfClient(fakeCPF));

        //When:
        Wine wine = clientService.getRecommendedWine(fakeCPF);

        //Then:
        assertEquals(expectedWine.getCodigo(), wine.getCodigo());
    }
}
