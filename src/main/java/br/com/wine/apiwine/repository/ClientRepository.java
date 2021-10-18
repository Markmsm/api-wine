package br.com.wine.apiwine.repository;

import br.com.wine.apiwine.data.model.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> getAll();
}
