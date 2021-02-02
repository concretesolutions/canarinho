package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorTelefone {

    @Test
    public void consegueFormatar() {
        assertThat(Formatador.TELEFONE.formata("1112345678"),
                is("(11) 1234-5678"));

        assertThat(Formatador.TELEFONE.formata("11123456789"),
                is("(11) 12345-6789"));

        try {
            Formatador.TELEFONE.formata(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDesformatar() {
        assertThat(Formatador.TELEFONE.desformata("(11) 1234-5678"),
                is("1112345678"));

        assertThat(Formatador.TELEFONE.desformata("(11) 12345-6789"),
                is("11123456789"));

        try {
            Formatador.TELEFONE.desformata(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSeEstaFormatado() {
        assertThat(Formatador.TELEFONE.estaFormatado("(11) 1234-5678"), is(true));
        assertThat(Formatador.TELEFONE.estaFormatado("(11) 12345-6789"), is(true));

        assertThat(Formatador.TELEFONE.estaFormatado("(11) 1234-56789"), is(false));
        assertThat(Formatador.TELEFONE.estaFormatado("1112345678"), is(false));
        assertThat(Formatador.TELEFONE.estaFormatado("11123456789"), is(false));

        try {
            Formatador.TELEFONE.estaFormatado(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {
        assertThat(Formatador.TELEFONE.podeSerFormatado("11"), is(false));
        assertThat(Formatador.TELEFONE.podeSerFormatado("111234567890"), is(false));

        assertThat(Formatador.TELEFONE.podeSerFormatado("1112345678"), is(true));
        assertThat(Formatador.TELEFONE.podeSerFormatado("11123456789"), is(true));

        try {
            Formatador.TELEFONE.podeSerFormatado(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

}
