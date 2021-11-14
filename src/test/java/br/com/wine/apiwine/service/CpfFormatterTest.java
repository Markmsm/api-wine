package br.com.wine.apiwine.service;

import org.junit.jupiter.api.Test;

import static br.com.wine.apiwine.service.CpfFormatter.formatCpf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CpfFormatterTest {

    //somente pontos
    //passando null

    @Test
    void formatCpfTesteeera() {
        //Given:
        String fakeCpf = "testing cpf formatter";

        //When:
        Throwable ex = assertThrows(NumberFormatException.class, () -> {
            String cpf = formatCpf(fakeCpf);
        });

        //Then:
        assertThat(ex.getMessage(), is("This cpf is not valid!"));
    }

    @Test
    void formatCpfShouldFormatWithMoreThanElevenChar() {
        //Given:
        String expectedCpf = "11122233344";

        //When:
        String cpf = formatCpf("0000000011122233344");

        //Then:
        assertThat(cpf, is(expectedCpf));
    }

    @Test
    void formatCpfShouldFormatRemovingDots() {
        //Given:
        String expectedCpf = "11122233344";

        //When:
        String cpf = formatCpf("111.222.333.44");

        //Then:
        assertThat(cpf, is(expectedCpf));
    }

    @Test
    void formatCpfShouldFormatRemovingHyphen() {
        //Given:
        String expectdCpf = "11122233344";

        //When:
        String cpf = formatCpf("111-222-333-44");

        //Then:
        assertThat(cpf, is(expectdCpf));
    }
}
