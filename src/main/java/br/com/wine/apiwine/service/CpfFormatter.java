package br.com.wine.apiwine.service;

public class CpfFormatter {

    public String formatCpf(String cpf) {
        cpf = cpf.replaceAll("\\.|-", "");

        if (!cpf.matches("^[0-9]*$")) throw new NumberFormatException("This cpf is not valid!");

        if (cpf.length() != 11) cpf = cpf.substring(cpf.length() - 11);

        return cpf;
    }
}
