package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.*;
import br.com.wine.apiwine.repository.ClientInCloudRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    static ClientInCloudRepository mockedClientInCloudRepository;
    static PurchaseServiceInCloud mockedPurchaseServiceInCloud;
    static ClientServiceInCloud clientServiceInCloud;
    static PurchaseCreator purchaseCreator;

    @BeforeAll
    static void setUp() {
        mockedClientInCloudRepository = mock(ClientInCloudRepository.class);
        mockedPurchaseServiceInCloud = mock(PurchaseServiceInCloud.class);
        clientServiceInCloud = new ClientServiceInCloud(mockedClientInCloudRepository, mockedPurchaseServiceInCloud);
        purchaseCreator = new PurchaseCreator();

        when(mockedClientInCloudRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseServiceInCloud.getAll()).thenReturn(purchaseCreator.getFakePurchases());
    }

    @Test
    void shouldReturnSortedClientsByMaxSpent() {
        List<Client> fakeClients = getFakeClients();
        List<Client> clients = clientServiceInCloud.getClientsSortedByMaxSpent();

        assertEquals(fakeClients.get(1).getId(), clients.get(0).getId());
        assertEquals(fakeClients.get(0).getId(), clients.get(1).getId());
    }

//    @Test
//    void shouldThrowExceptionIfNoClient() {
//        when(mockedClientInCloudRepository.getAll()).thenReturn(null);
//
//        Exception exception = assertThrows(ArithmeticException.class, () ->
//                clientServiceInCloud.getClientsSortedByMaxSpent());
//
//        assertEquals("No client!", exception.getMessage());
//    }

    @Test
    void shouldReturnClientWithMaxBuyInYear() {
        Client fakeClient = getFakeClients().get(1);
        Client clientWithMaxBuy = clientServiceInCloud.getClientWithMaxBuyInYear("2016");

        assertEquals(fakeClient.getId(), clientWithMaxBuy.getId());
    }

    @Test
    void shouldReturnLoyalClients() {
        Client fakeClient = getFakeClients().get(1);
        ArrayList<Client> loyalClients = clientServiceInCloud.getLoyalClients();

        assertEquals(1, loyalClients.size());
        assertEquals(fakeClient.getId(), loyalClients.get(0).getId());
    }

    @Test
    void shouldReturnRecommendedWine() {
        List<Client> fakeClients = getFakeClients();
        List<Purchase> fakePurchases = purchaseCreator.getFakePurchases();
        WineCreator wineCreator = new WineCreator();

        Wine fakeWineForClient1 = wineCreator.getFakeWines()[0];
        Wine fakeWineForClient2 = wineCreator.getFakeWines()[1];

        ArrayList<Purchase> client1Purchases = new ArrayList<>();
        ArrayList<Purchase> client2Purchases = new ArrayList<>();

        client1Purchases.add(fakePurchases.get(0));
        client1Purchases.add(fakePurchases.get(1));
        client2Purchases.add(fakePurchases.get(2));
        client2Purchases.add(fakePurchases.get(3));
        client2Purchases.add(fakePurchases.get(4));
        client2Purchases.add(fakePurchases.get(5));

        when(mockedPurchaseServiceInCloud.getClientPurchases(fakeClients.get(1).getCpf())).thenReturn(client1Purchases);
        when(mockedPurchaseServiceInCloud.getClientPurchases(fakeClients.get(0).getCpf())).thenReturn(client2Purchases);

        Wine recommendedWineForClient1 = clientServiceInCloud.getRecommendedWine(fakeClients.get(0).getCpf());
        Wine recommendedWineForClient2 = clientServiceInCloud.getRecommendedWine(fakeClients.get(1).getCpf());

        assertEquals(fakeWineForClient1.getCodigo(), recommendedWineForClient1.getCodigo());
        assertEquals(fakeWineForClient2.getCodigo(), recommendedWineForClient2.getCodigo());
    }

    private static List<Client> getFakeClients() {
        List<Client> clients = new ArrayList<>();
        Client client1 = new Client();
        Client client2 = new Client();

        client1.setId(1);
        client1.setNome("Vinicius");
        client1.setCpf("000.000.000-01");

        client2.setId(2);
        client2.setNome("Marcos");
        client2.setCpf("000.000.000-02");

        clients.add(client1);
        clients.add(client2);

        return clients;
    }
}
