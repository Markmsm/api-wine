package br.com.wine.apiwine.service;

public class cpfFormatter {

    public String formatCpf(String cpf) {
        cpf = cpf.replaceAll("\\.|-", "");

        if (cpf.length() != 11) cpf = cpf.substring(cpf.length() - 11);

        return cpf;
    }
}
