package br.com.wine.apiwine.data.model;

import java.util.ArrayList;
import java.util.List;

public class PurchaseCreator {

    public List<Purchase> getFakePurchases() {
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase1 = new Purchase();
        Purchase purchase2 = new Purchase();
        Purchase purchase3 = new Purchase();
        Purchase purchase4 = new Purchase();
        Purchase purchase5 = new Purchase();
        Purchase purchase6 = new Purchase();
        WineCreator wineCreator = new WineCreator();

        Wine[] fakeWines = wineCreator.getFakeWines();

        purchase1.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase1.setCliente("000.000.000.01");
        purchase1.setValorTotal(270.00);
        purchase1.setData("19-02-2019");
        purchase1.setItens(new Wine[] {fakeWines[0], fakeWines[1]});

        purchase2.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase2.setCliente("000.000.000.01");
        purchase2.setValorTotal(100.00);
        purchase2.setData("19-10-2016");
        purchase2.setItens(new Wine[] {fakeWines[1], fakeWines[2]});

        purchase3.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase3.setCliente("000.000.000.02");
        purchase3.setValorTotal(250.00);
        purchase3.setData("06-10-2016");
        purchase3.setItens(new Wine[] {fakeWines[0], fakeWines[1], fakeWines[2]});

        purchase4.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase4.setCliente("000.000.000.02");
        purchase4.setValorTotal(300.0);
        purchase4.setData("10-10-2020");
        purchase4.setItens(new Wine[] {fakeWines[0], fakeWines[1]});

        purchase5.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase5.setCliente("000.000.000.02");
        purchase5.setValorTotal(200.0);
        purchase5.setData("10-10-2018");
        purchase5.setItens(new Wine[] {fakeWines[0], fakeWines[2]});

        purchase6.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase6.setCliente("000.000.000.02");
        purchase6.setValorTotal(100.0);
        purchase6.setData("10-10-2015");
        purchase6.setItens(new Wine[] {fakeWines[0]});

        purchases.add(purchase1);
        purchases.add(purchase2);
        purchases.add(purchase3);
        purchases.add(purchase4);
        purchases.add(purchase5);
        purchases.add(purchase6);

        return purchases;
    }
}
