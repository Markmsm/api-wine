package br.com.wine.apiwine.data.model;

import java.util.Arrays;
import java.util.List;

public class WineCreator {

    public List<Wine> getFakeWines() {
        Wine wine1 = new Wine();
        Wine wine2 = new Wine();
        Wine wine3 = new Wine();

        wine1.setCodigo("a1f76d0e-c516-43c7-abe3-d3fa29d8eab4");
        wine1.setProduto("Hereford");
        wine1.setVariedade("Malbec");
        wine1.setPais("Argentina");
        wine1.setCategoria("Tinto");
        wine1.setSafra(2015);
        wine1.setPreco(49.89);

        wine2.setCodigo("8c76af8e-cde8-45aa-8aae-14e040080371");
        wine2.setProduto("Casa Perini");
        wine2.setVariedade("Cabernet Sauvignon");
        wine2.setPais("Brasil");
        wine2.setCategoria("Tinto");
        wine2.setSafra(2016);
        wine2.setPreco(40.00);

        wine3.setCodigo("67e6a4e2-4f77-4602-b8b0-f3fc98c5419b");
        wine3.setProduto("Nostros");
        wine3.setVariedade("Pinot Noir");
        wine3.setPais("Chile");
        wine3.setCategoria("Tinto");
        wine3.setSafra(2014);
        wine3.setPreco(53.79);

        return Arrays.asList(new Wine[] {wine1, wine2, wine3});
    }
}
