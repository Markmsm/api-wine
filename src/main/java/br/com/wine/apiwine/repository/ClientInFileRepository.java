package br.com.wine.apiwine.repository;

import br.com.wine.apiwine.data.model.Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientInFileRepository implements ClientRepository{

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();

        try {
            File clientsCSV = new File("/home/ubots/Learnspace/api-wine/src/main/files/clients.csv");
            Scanner reader = new Scanner(clientsCSV);
            reader.nextLine();

            while (reader.hasNextLine()) {
                String[] clientInfos = reader.nextLine().split(",");

                int clientId = Integer.parseInt(clientInfos[0]);
                String clientName = clientInfos[1];
                String clientCpf = clientInfos[2];

                clients.add(new Client(clientId, clientName, clientCpf));
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Deu ruim!!");
            e.printStackTrace();
        }
        return clients;
    }
}
