package br.com.concretesolutions.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import br.com.concretesolutions.canarinho.formatador.Formatador;
import br.com.concretesolutions.canarinho.formatador.FormatadorValor;
import br.com.concretesolutions.canarinho.sample.BuildConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class TesteFormatadorValor {

    @Test
    public void consegueFormatar() {

        assertThat(Formatador.VALOR.formata("1.00"), is("1,00"));
        assertThat(Formatador.VALOR.formata("1.0"), is("1,00"));
        assertThat(Formatador.VALOR.formata("1"), is("1,00"));
        assertThat(Formatador.VALOR.formata("1000"), is("1.000,00"));
        assertThat(Formatador.VALOR.formata("1.23"), is("1,23"));
        assertThat(Formatador.VALOR.formata("1.233"), is("1,23"));
        assertThat(Formatador.VALOR.formata("1.01"), is("1,01"));
        assertThat(Formatador.VALOR.formata("1.1"), is("1,10"));
        assertThat(Formatador.VALOR.formata("1234.56"), is("1.234,56"));
        assertThat(Formatador.VALOR.formata("1234.5"), is("1.234,50"));
    }

    @Test
    public void consegueFormatarComSimbolo() {

        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.00"), is("R$ 1,00"));
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.0"), is("R$ 1,00"));
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1"), is("R$ 1,00"));
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1000"), is("R$ 1.000,00"));
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.23"), is("R$ 1,23"));
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.233"), is("R$ 1,23"));
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.01"), is("R$ 1,01"));
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1.1"), is("R$ 1,10"));
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1234.56"), is("R$ 1.234,56"));
        assertThat(Formatador.VALOR_COM_SIMBOLO.formata("1234.5"), is("R$ 1.234,50"));
    }

    @Test
    public void consegueDesformatar() {
        assertThat(Formatador.VALOR.desformata("1,00"), is("1.00"));
        assertThat(Formatador.VALOR.desformata("1.234,10"), is("1234.10"));
        assertThat(Formatador.VALOR.desformata("0,10"), is("0.10"));
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        assertThat(Formatador.VALOR.estaFormatado("1,00"), is(true));
        assertThat(Formatador.VALOR.estaFormatado("1.234,10"), is(true));
        assertThat(Formatador.VALOR.estaFormatado("1.34,10"), is(false));

        try {
            Formatador.VALOR.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {

        assertThat(Formatador.VALOR.podeSerFormatado("1.00"), is(true));
        assertThat(Formatador.VALOR.podeSerFormatado("10"), is(true));
        assertThat(Formatador.VALOR.podeSerFormatado("10.1"), is(true));
        assertThat(Formatador.VALOR.podeSerFormatado("10,10"), is(false));

        try {
            Formatador.VALOR.podeSerFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }
}
