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
    void shouldReturnNoClientIfNotExistClient() {
        when(mockedClientRepository.getAll()).thenReturn(clientCreator.getFakeClientsEmpty());
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        assertTrue(clients.isEmpty());
    }

    @Test
    void shouldReturnNoClientsIfNotExistPurchase() {
        when(mockedPurchaseService.getAll()).thenReturn(purchaseCreator.getFakePurchasesEmpty());
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        assertTrue(clients.isEmpty());
    }

    @Test
    void shouldReturnClientsWithoutSpentInLastPositionSortedByMaxSpent() {
        List<Client> fakeClients = clientCreator.getFakeClients();
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        assertEquals(fakeClients.get(6).getId(), clients.get(5).getId());
        assertEquals(fakeClients.get(4).getId(), clients.get(6).getId());
    }

    @Test
    void shouldReturnSortedClientsByMaxSpent() {
        List<Client> fakeClients = clientCreator.getFakeClients();
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        assertEquals(7, clients.size());
        assertEquals(fakeClients.get(1).getId(), clients.get(0).getId());
        assertEquals(fakeClients.get(5).getId(), clients.get(1).getId());
        assertEquals(fakeClients.get(0).getId(), clients.get(2).getId());
        assertEquals(fakeClients.get(2).getId(), clients.get(3).getId());
        assertEquals(fakeClients.get(3).getId(), clients.get(4).getId());
    }

    @Test
    void shouldThrowExceptionIfNoExistsPurchase() {
        when(mockedPurchaseService.getAll()).thenReturn(purchaseCreator.getFakePurchasesEmpty());

        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Client client = clientService.getClientWithMaxBuyInYear("2016");
        });

        assertEquals("There is no purchase!", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionIfNoExistsClient() {
        when(mockedClientRepository.getAll()).thenReturn(clientCreator.getFakeClientsEmpty());

        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Client client = clientService.getClientWithMaxBuyInYear("2016");
        });

        assertEquals("There is no client!", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionIfNoExistsBuyInYear() {
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Client client = clientService.getClientWithMaxBuyInYear("1994");
        });

        assertEquals("Are not purchases this year!", ex.getMessage());
    }

    @Test
    void shouldReturnClientWithMaxBuyInYear() throws Exception {
        int fakeClientId = clientCreator.getFakeClients().get(1).getId();
        int clientWithMaxBuyId = clientService.getClientWithMaxBuyInYear("2016").getId();

        assertEquals(fakeClientId, clientWithMaxBuyId);
    }

    @Test
    void shouldReturnLoyalClients() {
        List<Client> fakeClients = clientCreator.getFakeClients();
        List<Client> loyalClients = clientService.getLoyalClients();

        assertEquals(2, loyalClients.size());
        assertEquals(fakeClients.get(1).getId(), loyalClients.get(0).getId());
        assertEquals(fakeClients.get(2).getId(), loyalClients.get(1).getId());
    }

    @Test
    void shouldThrowExceptionIfClientHaveNotPurchase() {
        Throwable ex = assertThrows(NoSuchElementException.class, () -> {
            Optional<Wine> wine = clientService.getRecommendedWine("000.000.000-08");
        });

        assertEquals("This client haven't purchase!", ex.getMessage());
    }

    @Test
    void shouldReturnRecommendedWine() {
        String fakeCPF = "000.000.000-02";
        when(mockedPurchaseService.getClientPurchases(fakeCPF)).thenReturn(purchaseCreator.getPurchasesOfClient(fakeCPF));

        Optional<Wine> wine = clientService.getRecommendedWine(fakeCPF);
        Wine expectedWine = new WineCreator().getFakeWines().get(0);

        assertEquals(expectedWine.getCodigo(), wine.get().getCodigo());


//        List<Client> fakeClients = clientCreator.getFakeClients();
//        List<Purchase> fakePurchases = purchaseCreator.getFakePurchases();
//        WineCreator wineCreator = new WineCreator();
//
//        Wine fakeWineForClient1 = wineCreator.getFakeWines()[0];
//        Wine fakeWineForClient2 = wineCreator.getFakeWines()[1];
//
//        ArrayList<Purchase> client1Purchases = new ArrayList<>();
//        ArrayList<Purchase> client2Purchases = new ArrayList<>();
//
//        client1Purchases.add(fakePurchases.get(0));
//        client1Purchases.add(fakePurchases.get(1));
//        client2Purchases.add(fakePurchases.get(2));
//        client2Purchases.add(fakePurchases.get(3));
//        client2Purchases.add(fakePurchases.get(4));
//        client2Purchases.add(fakePurchases.get(5));
//
//        when(mockedPurchaseService.getClientPurchases(fakeClients.get(1).getCpf())).thenReturn(client1Purchases);
//        when(mockedPurchaseService.getClientPurchases(fakeClients.get(0).getCpf())).thenReturn(client2Purchases);
//
//        Wine recommendedWineForClient1 = clientService.getRecommendedWine(fakeClients.get(0).getCpf());
//        Wine recommendedWineForClient2 = clientService.getRecommendedWine(fakeClients.get(1).getCpf());
//
//        assertEquals(fakeWineForClient1.getCodigo(), recommendedWineForClient1.getCodigo());
//        assertEquals(fakeWineForClient2.getCodigo(), recommendedWineForClient2.getCodigo());
    }
}
