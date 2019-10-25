package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import br.com.concrete.canarinho.formatador.Formatador;
import br.com.concrete.canarinho.sample.BuildConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class TesteFormatadorTelefone {

    @Test
    public void consegueFormatar() {
        assertThat(Formatador.Constantes.TELEFONE.formata("1112345678"),
                is("(11) 1234-5678"));

        assertThat(Formatador.Constantes.TELEFONE.formata("11123456789"),
                is("(11) 12345-6789"));

        try {
            Formatador.Constantes.TELEFONE.formata(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDesformatar() {
        assertThat(Formatador.Constantes.TELEFONE.desformata("(11) 1234-5678"),
                is("1112345678"));

        assertThat(Formatador.Constantes.TELEFONE.desformata("(11) 12345-6789"),
                is("11123456789"));

        try {
            Formatador.Constantes.TELEFONE.desformata(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSeEstaFormatado() {
        assertThat(Formatador.Constantes.TELEFONE.estaFormatado("(11) 1234-5678"), is(true));
        assertThat(Formatador.Constantes.TELEFONE.estaFormatado("(11) 12345-6789"), is(true));

        assertThat(Formatador.Constantes.TELEFONE.estaFormatado("(11) 1234-56789"), is(false));
        assertThat(Formatador.Constantes.TELEFONE.estaFormatado("1112345678"), is(false));
        assertThat(Formatador.Constantes.TELEFONE.estaFormatado("11123456789"), is(false));

        try {
            Formatador.Constantes.TELEFONE.estaFormatado(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {
        assertThat(Formatador.Constantes.TELEFONE.podeSerFormatado("11"), is(false));
        assertThat(Formatador.Constantes.TELEFONE.podeSerFormatado("111234567890"), is(false));

        assertThat(Formatador.Constantes.TELEFONE.podeSerFormatado("1112345678"), is(true));
        assertThat(Formatador.Constantes.TELEFONE.podeSerFormatado("11123456789"), is(true));

        try {
            Formatador.Constantes.TELEFONE.podeSerFormatado(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

}
