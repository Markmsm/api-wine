package br.com.wine.apiwine.service;

public class CpfFormatter {

    static public String formatCpf(String cpf) {
        String formattedCpf = cpf.replaceAll("\\.|-", "");

        if (!formattedCpf.matches("^[0-9]*$")) throw new NumberFormatException("This cpf is not valid!");

        if (formattedCpf.length() != 11) formattedCpf = formattedCpf.substring(cpf.length() - 11);

        return formattedCpf;
    }
}
