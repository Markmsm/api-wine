package br.com.wine.apiwine.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CpfFormatterTest {

    private static CpfFormatter cpfFormatter;

    @BeforeAll
    static void setUp() {
        cpfFormatter = new CpfFormatter();
    }

    @Test
    void formatCpfTesteeera() {
        Throwable ex = assertThrows(NumberFormatException.class, () -> {
            String cpf = cpfFormatter.formatCpf("testing cpf formatter");
        });

        assertEquals("This cpf is not valid!", ex.getMessage());
    }

    @Test
    void formatCpfShouldFormatWithMoreThanElevenChar() {
        String expectedCpf = "11122233344";
        String cpf = cpfFormatter.formatCpf("0000000011122233344");

        assertEquals(expectedCpf, cpf);
    }

    @Test
    void formatCpfShouldFormatRemovingDots() {
        String expectedCpf = "11122233344";
        String cpf = cpfFormatter.formatCpf("111.222.333.44");

        assertEquals(expectedCpf, cpf);
    }

    @Test
    void formatCpfShouldFormatRemovingHyphen() {
        String expectdCpf = "11122233344";
        String cpf = cpfFormatter.formatCpf("111-222-333-44");

        assertEquals(expectdCpf, cpf);
    }
}
