package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorTelefone {

    @Test
    public void consegueFormatar() {
        assertThat(Formatador.TELEFONE.formata("1112345678"))
                .isEqualTo("(11) 1234-5678");

        assertThat(Formatador.TELEFONE.formata("11123456789"))
                .isEqualTo("(11) 12345-6789");

        try {
            Formatador.TELEFONE.formata(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDesformatar() {
        assertThat(Formatador.TELEFONE.desformata("(11) 1234-5678"))
                .isEqualTo("1112345678");

        assertThat(Formatador.TELEFONE.desformata("(11) 12345-6789"))
                .isEqualTo("11123456789");

        try {
            Formatador.TELEFONE.desformata(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSeEstaFormatado() {
        assertThat(Formatador.TELEFONE.estaFormatado("(11) 1234-5678")).isTrue();
        assertThat(Formatador.TELEFONE.estaFormatado("(11) 12345-6789")).isTrue();

        assertThat(Formatador.TELEFONE.estaFormatado("(11) 1234-56789")).isFalse();
        assertThat(Formatador.TELEFONE.estaFormatado("1112345678")).isFalse();
        assertThat(Formatador.TELEFONE.estaFormatado("11123456789")).isFalse();

        try {
            Formatador.TELEFONE.estaFormatado(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {
        assertThat(Formatador.TELEFONE.podeSerFormatado("11")).isFalse();
        assertThat(Formatador.TELEFONE.podeSerFormatado("111234567890")).isFalse();

        assertThat(Formatador.TELEFONE.podeSerFormatado("1112345678")).isTrue();
        assertThat(Formatador.TELEFONE.podeSerFormatado("11123456789")).isTrue();

        try {
            Formatador.TELEFONE.podeSerFormatado(null);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

}
