package br.com.wine.apiwine.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseCreator {

    static public List<Purchase> getFakePurchases() {
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase1OfClient1 = new Purchase();
        Purchase purchase2OfClient1 = new Purchase();
        Purchase purchase1OfClient2 = new Purchase();
        Purchase purchase2OfClient2 = new Purchase();
        Purchase purchase3OfClient2 = new Purchase();
        Purchase purchase4OfClient2 = new Purchase();
        Purchase purchase5OfClient2 = new Purchase();
        Purchase purchase1OfClient3 = new Purchase();
        Purchase purchase2OfClient3 = new Purchase();
        Purchase purchase3OfClient3 = new Purchase();
        Purchase purchase4OfClient3 = new Purchase();
        Purchase purchase1OfClient4 = new Purchase();
        Purchase purchase2OfClient4 = new Purchase();
        Purchase purchase1OfClient6 = new Purchase();
        Purchase purchase1OfClient7 = new Purchase();
//        Purchase purchase16 = new Purchase();
//        Purchase purchase17 = new Purchase();
//        Purchase purchase18 = new Purchase();
//        Purchase purchase19 = new Purchase();
//        Purchase purchase20 = new Purchase();
        WineCreator wineCreator = new WineCreator();

        List<Wine> fakeWines = wineCreator.getFakeWines();

        purchase1OfClient1.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase1OfClient1.setCliente("000.000.000.01");
        purchase1OfClient1.setValorTotal(270.00);
        purchase1OfClient1.setData("19-02-2019");
        purchase1OfClient1.addItem(fakeWines.get(0));
        purchase1OfClient1.addItem(fakeWines.get(1));


        purchase2OfClient1.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase2OfClient1.setCliente("000.000.000.01");
        purchase2OfClient1.setValorTotal(100.00);
        purchase2OfClient1.setData("19-10-2016");
        purchase2OfClient1.addItem(fakeWines.get(1));
        purchase2OfClient1.addItem(fakeWines.get(2));


        purchase1OfClient2.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase1OfClient2.setCliente("000.000.000.02");
        purchase1OfClient2.setValorTotal(250.00);
        purchase1OfClient2.setData("06-10-2016");
        purchase1OfClient2.addItem(fakeWines.get(0));
        purchase1OfClient2.addItem(fakeWines.get(1));
        purchase1OfClient2.addItem(fakeWines.get(2));

        purchase2OfClient2.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase2OfClient2.setCliente("000.000.000.02");
        purchase2OfClient2.setValorTotal(300.0);
        purchase2OfClient2.setData("10-10-2020");
        purchase2OfClient2.addItem(fakeWines.get(0));
        purchase2OfClient2.addItem(fakeWines.get(1));

        purchase3OfClient2.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase3OfClient2.setCliente("000.000.000.02");
        purchase3OfClient2.setValorTotal(200.0);
        purchase3OfClient2.setData("10-10-2018");
        purchase3OfClient2.addItem(fakeWines.get(0));
        purchase3OfClient2.addItem(fakeWines.get(2));

        purchase4OfClient2.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase4OfClient2.setCliente("000.000.000.02");
        purchase4OfClient2.setValorTotal(100.0);
        purchase4OfClient2.setData("10-10-2020");
        purchase4OfClient2.addItem(fakeWines.get(1));

        purchase5OfClient2.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase5OfClient2.setCliente("000.000.000.02");
        purchase5OfClient2.setValorTotal(1000.0);
        purchase5OfClient2.setData("10-10-2019");
        purchase5OfClient2.addItem(fakeWines.get(0));
        purchase5OfClient2.addItem(fakeWines.get(2));

        purchase1OfClient3.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase1OfClient3.setCliente("000.000.000.03");
        purchase1OfClient3.setValorTotal(50.0);
        purchase1OfClient3.setData("10-10-2020");
        purchase1OfClient3.addItem(fakeWines.get(2));

        purchase2OfClient3.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase2OfClient3.setCliente("000.000.000.03");
        purchase2OfClient3.setValorTotal(35.0);
        purchase2OfClient3.setData("10-10-2017");
        purchase2OfClient3.addItem(fakeWines.get(2));

        purchase3OfClient3.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase3OfClient3.setCliente("000.000.000.03");
        purchase3OfClient3.setValorTotal(75.0);
        purchase3OfClient3.setData("10-10-2016");
        purchase3OfClient3.addItem(fakeWines.get(1));

        purchase4OfClient3.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase4OfClient3.setCliente("000.000.000.03");
        purchase4OfClient3.setValorTotal(100.0);
        purchase4OfClient3.setData("10-10-2015");
        purchase4OfClient3.addItem(fakeWines.get(0));

        purchase1OfClient4.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase1OfClient4.setCliente("000.000.000.04");
        purchase1OfClient4.setValorTotal(100.0);
        purchase1OfClient4.setData("10-10-2021");
        purchase1OfClient4.addItem(fakeWines.get(0));

        purchase2OfClient4.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase2OfClient4.setCliente("000.000.000.04");
        purchase2OfClient4.setValorTotal(100.0);
        purchase2OfClient4.setData("10-10-2018");
        purchase2OfClient4.addItem(fakeWines.get(0));

        purchase1OfClient6.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase1OfClient6.setCliente("000.000.000.06");
        purchase1OfClient6.setValorTotal(400.0);
        purchase1OfClient6.setData("10-10-2020");
        purchase1OfClient6.addItem(fakeWines.get(1));

        purchase1OfClient7.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchase1OfClient7.setCliente("000.000.000.07");
        purchase1OfClient7.setValorTotal(575.00);
        purchase1OfClient7.setData("07-12-2021");
        purchase1OfClient7.addItem(fakeWines.get(2));

//        purchase15.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
//        purchase15.setCliente("000.000.000.02");
//        purchase15.setValorTotal(100.0);
////        purchase15.setData("10-10-2015");
////        purchase15.setItens(new Wine[] {fakeWines[0]});
//
//        purchase16.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
//        purchase16.setCliente("000.000.000.02");
//        purchase16.setValorTotal(100.0);
////        purchase16.setData("10-10-2015");
////        purchase16.setItens(new Wine[] {fakeWines[0]});
//
//        purchase17.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
//        purchase17.setCliente("000.000.000.02");
//        purchase17.setValorTotal(100.0);
////        purchase17.setData("10-10-2015");
////        purchase17.setItens(new Wine[] {fakeWines[0]});
//
//        purchase18.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
//        purchase18.setCliente("000.000.000.02");
//        purchase18.setValorTotal(100.0);
////        purchase18.setData("10-10-2015");
////        purchase18.setItens(new Wine[] {fakeWines[0]});
//
//        purchase19.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
//        purchase19.setCliente("000.000.000.02");
//        purchase19.setValorTotal(100.0);
////        purchase19.setData("10-10-2015");
////        purchase19.setItens(new Wine[] {fakeWines[0]});
//
//        purchase20.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
//        purchase20.setCliente("000.000.000.02");
//        purchase20.setValorTotal(100.0);
////        purchase20.setData("10-10-2015");
////        purchase20.setItens(new Wine[] {fakeWines[0]});

        purchases.add(purchase1OfClient1);
        purchases.add(purchase2OfClient1);
        purchases.add(purchase1OfClient2);
        purchases.add(purchase2OfClient2);
        purchases.add(purchase3OfClient2);
        purchases.add(purchase4OfClient2);
        purchases.add(purchase5OfClient2);
        purchases.add(purchase1OfClient3);
        purchases.add(purchase2OfClient3);
        purchases.add(purchase3OfClient3);
        purchases.add(purchase4OfClient3);
        purchases.add(purchase1OfClient4);
        purchases.add(purchase2OfClient4);
        purchases.add(purchase1OfClient6);
        purchases.add(purchase1OfClient7);

        return purchases;
    }

    static public List<Purchase> getFakePurchasesEmpty() {
        List<Purchase> purchases = new ArrayList<>();
        return purchases;
    }

    static public List<Purchase> getPurchasesOfClient(String cpf) {
        return getFakePurchases()
                .stream()
                .filter(p -> formatCpf(cpf).equals(formatCpf(p.getCliente())))
                .collect(Collectors.toList());
    }

    static public List<Purchase> getFakePurchasesWithNonExistingClient() {
        List<Purchase> purchases = getFakePurchases();
        Purchase purchaseOfNonExistingClient = new Purchase();

        purchaseOfNonExistingClient.setCodigo("3fde36a6-c9a1-4d27-9f0f-7c12ab0d1cdd");
        purchaseOfNonExistingClient.setCliente("000.000.000.99");
        purchaseOfNonExistingClient.setValorTotal(575.00);
        purchaseOfNonExistingClient.setData("07-12-2021");

        return purchases;
    }

    static private String formatCpf(String cpf) {
        if (cpf.length() != 14) {
            cpf = cpf.substring(1, 15);
        }
        return cpf.replaceAll("\\.|-", "");
    }

}
