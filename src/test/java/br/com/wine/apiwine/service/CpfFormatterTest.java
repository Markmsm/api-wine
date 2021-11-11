package br.com.wine.apiwine.service;

import org.junit.jupiter.api.Test;

import static br.com.wine.apiwine.service.CpfFormatter.formatCpf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CpfFormatterTest {

    //somente pontos
    //passando null

    @Test
    void formatCpfTesteeera() {
        //When:
        Throwable ex = assertThrows(NumberFormatException.class, () -> {
            String cpf = formatCpf("testing cpf formatter");
        });

        //Then:
        assertEquals("This cpf is not valid!", ex.getMessage());
    }

    @Test
    void formatCpfShouldFormatWithMoreThanElevenChar() {
        //Given:
        String expectedCpf = "11122233344";

        //When:
        String cpf = formatCpf("0000000011122233344");

        //Then:
        assertEquals(expectedCpf, cpf);
    }

    @Test
    void formatCpfShouldFormatRemovingDots() {
        //Given:
        String expectedCpf = "11122233344";

        //When:
        String cpf = formatCpf("111.222.333.44");

        //Then:
        assertEquals(expectedCpf, cpf);
    }

    @Test
    void formatCpfShouldFormatRemovingHyphen() {
        //Given:
        String expectdCpf = "11122233344";

        //When:
        String cpf = formatCpf("111-222-333-44");

        //Then:
        assertEquals(expectdCpf, cpf);
    }
}
