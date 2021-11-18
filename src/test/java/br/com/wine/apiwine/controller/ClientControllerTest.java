package br.com.wine.apiwine.controller;

import br.com.wine.apiwine.repository.ClientRepository;
import br.com.wine.apiwine.repository.PurchaseRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.wine.apiwine.data.model.ClientCreator.getFakeClients;
import static br.com.wine.apiwine.data.model.ClientCreator.getFakeClientsSortedByMaxSpent;
import static br.com.wine.apiwine.data.model.PurchaseCreator.getFakePurchases;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    void getClientsSortedByMaxSpentShouldReturnSortedClientsByMaxSpent() throws Exception {
        when(mockedClientRepository.getAll()).thenReturn(getFakeClients());
        when(mockedPurchaseRepository.getAll()).thenReturn(getFakePurchases());

        String expectedClientsJson = gson.toJson(getFakeClientsSortedByMaxSpent());

        this.mockMvc
                .perform(get("/api/clients/v1/max-spent"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedClientsJson, true));
    }

    @Test
    void getClientWithUniqueHighestBuy() {
    }

    @Test
    void getLoyalClients() {
    }

    @Test
    void getRecommendedWine() {
    }
}