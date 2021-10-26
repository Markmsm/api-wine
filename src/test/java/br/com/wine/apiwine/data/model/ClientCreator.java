package br.com.wine.apiwine.data.model;

import java.util.ArrayList;
import java.util.List;

public class ClientCreator {

    public List<Client> getFakeClients() {
        List<Client> clients = new ArrayList<>();
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        Client client4 = new Client();
        Client client5 = new Client();
        Client client6 = new Client();
        Client client8 = new Client();

        client1.setId(1);
        client1.setName("Vinicius");
        client1.setCpf("000.000.000-01");

        client2.setId(2);
        client2.setName("Marcos");
        client2.setCpf("000.000.000-02");

        client3.setId(3);
        client3.setName("Gustavo");
        client3.setCpf("000.000.000-03");

        client4.setId(4);
        client4.setName("Matheus");
        client4.setCpf("000.000.000-04");

        client5.setId(5);
        client5.setName("Mark");
        client5.setCpf("000.000.000-05");

        client6.setId(6);
        client6.setName("Junesval");
        client6.setCpf("000.000.000-06");

        client8.setId(8);
        client8.setName("Beatriz");
        client8.setCpf("000.000.000-08");

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);
        clients.add(client6);
        clients.add(client8);

        return clients;
    }

    public List<Client> getFakeClientsEmpty() {
        List<Client> clients = new ArrayList<>();
        return clients;
    }
}
