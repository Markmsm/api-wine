package br.com.wine.apiwine.data.model;

import java.util.Objects;

public class TempClient {

    private Client client;
    private Double totalExpended;
    private int buyTimes;

    public TempClient(Client client, Double totalExpended) {
        this.client = client;
        this.totalExpended = totalExpended;
    }

    public TempClient(Client client, int buyTimes) {
        this.client = client;
        this.buyTimes = buyTimes;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getTotalExpended() {
        return totalExpended;
    }

    public void setTotalExpended(Double totalExpended) {
        this.totalExpended = totalExpended;
    }

    public int getBuyTimes() {
        return buyTimes;
    }

    public void setBuyTimes(int buyTimes) {
        this.buyTimes = buyTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempClient that = (TempClient) o;
        return buyTimes == that.buyTimes && Objects.equals(client, that.client) && Objects.equals(totalExpended, that.totalExpended);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, totalExpended, buyTimes);
    }
}
