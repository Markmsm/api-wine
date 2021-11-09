package br.com.wine.apiwine.repository;

import br.com.wine.apiwine.data.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientInFileRepository implements ClientRepository{

    Scanner reader;

    public ClientInFileRepository(Scanner reader) {
        this.reader = reader;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();

        reader.nextLine();

        while (reader.hasNextLine()) {
            String[] clientInfos = reader.nextLine().split(",");

            int clientId = Integer.parseInt(clientInfos[0]);
            String clientName = clientInfos[1];
            String clientCpf = clientInfos[2];

            clients.add(new Client(clientId, clientName, clientCpf));
        }
        reader.close();

        return clients;
    }
}
