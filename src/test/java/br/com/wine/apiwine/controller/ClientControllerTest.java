package br.com.wine.apiwine.controller;

import br.com.wine.apiwine.data.model.WineCreator;
import br.com.wine.apiwine.repository.ClientRepository;
import br.com.wine.apiwine.repository.PurchaseRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.wine.apiwine.data.model.ClientCreator.*;
import static br.com.wine.apiwine.data.model.PurchaseCreator.getFakePurchases;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository mockedClientRepository;

    @MockBean
    private PurchaseRepository mockedPurchaseRepository;

    Gson gson = new Gson();
    String clientApiPath = "/api/clients/v1";

    @Test
    void getClientsSortedByMaxSpentShouldReturnSortedClientsByMaxSpent() throws Exception {
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        String expectedClientsJson = gson.toJson(getFakeClientsSortedByMaxSpent());

        this.mockMvc
                .perform(get(clientApiPath + "/max-spent"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedClientsJson, true));
    }

    @Test
    void getClientWithUniqueHighestBuyInYearShouldReturnNotFoundIfIncorrectYear() throws Exception {
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        String year = "123456789";

        this.mockMvc
                .perform(get(clientApiPath + "/max-purchase/" + year))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(
                        result.getResponse().getErrorMessage(),
                        is("There are no client or purchase in " + year))
                );
    }

    @Test
    void getClientWithUniqueHighestBuyInYearShouldReturnNotFoundIfYearIsNotNumber() throws Exception {
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        String year = "ano com letras e ..., |||, @@@!";

        this.mockMvc
                .perform(get(clientApiPath + "/max-purchase/" + year))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(
                        result.getResponse().getErrorMessage(),
                        is("There are no client or purchase in " + year))
                );
    }

    @Test
    void getClientWithUniqueHighestBuyInYearShouldReturnClientWithMaxBuyInYear() throws Exception {
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        int expectedClientId = getFakeClients().get(1).getId();

        this.mockMvc
                .perform(get(clientApiPath + "/max-purchase/2016"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedClientId)));
    }

    @Test
    void getLoyalClientsShouldReturnLoyalClients() throws Exception {
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        String expectedClientsJson = gson.toJson(getFakeLoyalClients());

        this.mockMvc
                .perform(get(clientApiPath + "/loyal-clients"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedClientsJson, true));
    }

    @Test
    void getRecommendedWineShouldReturnNotFoundIfCpfIsNotNumber() throws Exception {
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        mockMvc
                .perform(get(clientApiPath + "/wine/abcdef..@@!!|fhak"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(
                        result.getResponse().getErrorMessage(),
                        is("This client haven't purchase yet!")
                ));
    }

    @Test
    void getRecommendedWineShouldReturnNotFoundIfNoBuyForCustomer() throws Exception {
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        mockMvc
                .perform(get(clientApiPath + "/wine/99999999999"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(
                        result.getResponse().getErrorMessage(),
                        is("This client haven't purchase yet!")
                ));
    }

    @Test
    void getRecommendedWineShouldReturnRecommendedWine() throws Exception {
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        String expectedWineCode = new WineCreator().getFakeWines().get(0).getCodigo();

        mockMvc
                .perform(get(clientApiPath + "/wine/00000000002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is(expectedWineCode)));
    }
}