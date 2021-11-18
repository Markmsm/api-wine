package br.com.wine.apiwine.service;

import br.com.wine.apiwine.data.model.*;
import br.com.wine.apiwine.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static br.com.wine.apiwine.data.model.ClientCreator.*;
import static br.com.wine.apiwine.data.model.PurchaseCreator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
        assertThat(ex.getMessage(), is("There is no client!"));
    }

    @Test
    void getAllShouldReturnClients() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        List<Client> expectedClients = getFakeClients();

        //When:
        List<Client> clients = clientService.getAll();

        //Then:
        assertThat(clients, is(expectedClients));
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnNoClientIfNotExistClient() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClientsEmpty());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());

        //When:
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        //Then:
        assertThat(clients, empty());
//        assertTrue(clients.isEmpty());
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnNoClientIfNotExistPurchase() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchasesEmpty());

        //When:
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        //Then:
        assertThat(clients, empty());
        //assertTrue(clients.isEmpty());
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
        assertThat(clients.get(5).getId(), is(expectedClients.get(6).getId()));
        assertThat(clients.get(6).getId(), is(expectedClients.get(4).getId()));
    }

    @Test
    void getClientsSortedByMaxSpentShouldReturnSortedClientsByMaxSpent() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());
        List<Client> expectedClients = getFakeClientsSortedByMaxSpent();

        //When:
        List<Client> clients = clientService.getClientsSortedByMaxSpent();

        //Then:
        assertThat(clients, is(expectedClients));
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
        assertThat(ex.getMessage(), is("There are no client or purchase in " + year));
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
        assertThat(ex.getMessage(), is("There are no client or purchase in " + year));
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
        assertThat(ex.getMessage(), is("There are no client or purchase in " + year));
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
        assertThat(clientWithMaxBuyId, is(expectedClientId));
    }

    @Test
    void getLoyalClientsShouldReturnNoClientIfNotExistClient() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClientsEmpty());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchases());

        //When:
        List<Client> clients = clientService.getLoyalClients();

        //Then:
        assertThat(clients, empty());
//        assertTrue(clients.isEmpty());
    }

    @Test
    void getLoyalClientsShouldReturnNoClientIfNotExistPurchase() {
        //Given:
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseService.getAll()).thenReturn(getFakePurchasesEmpty());

        //When:
        List<Client> clients = clientService.getLoyalClients();

        //Then:
        assertThat(clients, empty());
//        assertTrue(clients.isEmpty());
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
        //@Todo: criar métodos para retornar fakeLoyalClients ordenados para este teste
//        assertThat(loyalClients, is(expectedClients));
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
            Wine wine = clientService.getRecommendedWine("00000000008");
        });

        //Then:
        assertThat(ex.getMessage(), is("This client haven't purchase yet!"));
    }

    @Test
    void getRecommendedWineShouldReturnRecommendedWine() {
        //Given:
        String fakeCPF = "00000000002";
        String expectedWineCode = new WineCreator().getFakeWines().get(0).getCodigo();
        when(mockedPurchaseService.getClientPurchases(fakeCPF)).thenReturn(getPurchasesOfClient(fakeCPF));

        //When:
        String wineCode = clientService.getRecommendedWine(fakeCPF).getCodigo();

        //Then:
        assertThat(wineCode, is(expectedWineCode));
    }
}
