package br.com.wine.apiwine.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Purchase {

    private String codigo;
    private String data;
    private String cliente;
    private List<Wine> itens;
    private Double valorTotal;

    public Purchase() {
        this.itens = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<Wine> getItens() {
        return itens;
    }

    public void setItens(List<Wine> itens) {
        this.itens = itens;
    }

    public void addItem(Wine wine) {
        this.itens.add(wine);
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(codigo, purchase.codigo) && Objects.equals(data, purchase.data) && Objects.equals(cliente, purchase.cliente) && Objects.equals(itens, purchase.itens) && Objects.equals(valorTotal, purchase.valorTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, data, cliente, itens, valorTotal);
    }
}
