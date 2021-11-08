package br.com.wine.apiwine.repository;

import br.com.wine.apiwine.data.model.Purchase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseInFileRepository implements PurchaseRepository {

    FileReader reader;
    Gson gson;

    public PurchaseInFileRepository(FileReader reader, Gson gson) {
        this.reader = reader;
        this.gson = gson;
    }

    @Override
    public List<Purchase> getAll() {
        try {
            List<Purchase> purchases = gson.fromJson(reader, new TypeToken<List<Purchase>>() {}.getType());
            reader.close();
            return purchases;
        } catch (IOException e) {
            System.out.println("Deu ruim!!!");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
