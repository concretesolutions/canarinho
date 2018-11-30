package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorValor {

    @Test
    public void consegueFormatar() {

        assertThat(Formatador.VALOR.formata("1.00")).isEqualTo("1,00");
        assertThat(Formatador.VALOR.formata("1.0")).isEqualTo("1,00");
        assertThat(Formatador.VALOR.formata("1")).isEqualTo("1,00");
        assertThat(Formatador.VALOR.formata("1000")).isEqualTo("1.000,00");
        assertThat(Formatador.VALOR.formata("1.23")).isEqualTo("1,23");
        assertThat(Formatador.VALOR.formata("1.233")).isEqualTo("1,23");
        assertThat(Formatador.VALOR.formata("1.01")).isEqualTo("1,01");
        assertThat(Formatador.VALOR.formata("1.1")).isEqualTo("1,10");
        assertThat(Formatador.VALOR.formata("1234.56")).isEqualTo("1.234,56");
        assertThat(Formatador.VALOR.formata("1234.5")).isEqualTo("1.234,50");
    }

    @Test
    public void consegueFormatarComSimbolo() {

        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.00")).isEqualTo("R$ 1,00");
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.0")).isEqualTo("R$ 1,00");
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1")).isEqualTo("R$ 1,00");
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1000")).isEqualTo("R$ 1.000,00");
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.23")).isEqualTo("R$ 1,23");
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.233")).isEqualTo("R$ 1,23");
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.01")).isEqualTo("R$ 1,01");
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.1")).isEqualTo("R$ 1,10");
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1234.56")).isEqualTo("R$ 1.234,56");
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1234.5")).isEqualTo("R$ 1.234,50");
    }

    @Test
    public void consegueDesformatar() {
        assertThat(Formatador.VALOR.desformata("1,00")).isEqualTo("1.00");
        assertThat(Formatador.VALOR.desformata("1.234,10")).isEqualTo("1234.10");
        assertThat(Formatador.VALOR.desformata("0,10")).isEqualTo("0.10");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        assertThat(Formatador.VALOR.estaFormatado("1,00")).isTrue();
        assertThat(Formatador.VALOR.estaFormatado("1.234,10")).isTrue();
        assertThat(Formatador.VALOR.estaFormatado("1.34,10")).isFalse();

        try {
            Formatador.VALOR.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {

        assertThat(Formatador.VALOR.podeSerFormatado("1.00")).isTrue();
        assertThat(Formatador.VALOR.podeSerFormatado("10")).isTrue();
        assertThat(Formatador.VALOR.podeSerFormatado("10.1")).isTrue();
        assertThat(Formatador.VALOR.podeSerFormatado("10,10")).isFalse();

        try {
            Formatador.VALOR.podeSerFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }
}
