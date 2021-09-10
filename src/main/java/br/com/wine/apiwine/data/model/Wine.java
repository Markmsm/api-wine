package br.com.wine.apiwine.data.model;

import java.util.Objects;

public class Wine {

    private String codigo;
    private String produto;
    private String variedade;
    private String pais;
    private String categoria;
    private int safra;
    private Double preco;

    public Wine() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getVariedade() {
        return variedade;
    }

    public void setVariedade(String variedade) {
        this.variedade = variedade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getSafra() {
        return safra;
    }

    public void setSafra(int safra) {
        this.safra = safra;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wine wine = (Wine) o;
        return safra == wine.safra && Objects.equals(codigo, wine.codigo) && Objects.equals(produto, wine.produto) && Objects.equals(variedade, wine.variedade) && Objects.equals(pais, wine.pais) && Objects.equals(categoria, wine.categoria) && Objects.equals(preco, wine.preco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, produto, variedade, pais, categoria, safra, preco);
    }
}
