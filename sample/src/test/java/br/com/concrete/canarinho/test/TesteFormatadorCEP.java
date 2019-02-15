package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorCEP {

    @Test
    public void consegueFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CEP.formata("00000-000"), is("00000-000"));
        assertThat(Formatador.CEP.formata("00000000"), is("00000-000"));

        assertThat(Formatador.CEP.formata("12345-123"), is("12345-123"));
        assertThat(Formatador.CEP.formata("12345678"), is("12345-678"));

        assertThrowsFormat("");
        assertThrowsFormat("123123");
    }

    @Test
    public void consegueDesformatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CEP.desformata("00000-000"), is("00000000"));
        assertThat(Formatador.CEP.desformata("00000000"), is("00000000"));

        assertThat(Formatador.CEP.desformata("12345-123"), is("12345123"));
        assertThat(Formatador.CEP.desformata("12345678"), is("12345678"));

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CEP.estaFormatado("12345-678"), is(true));
        assertThat(Formatador.CEP.estaFormatado("12345678"), is(false));
        assertThat(Formatador.CEP.estaFormatado("00000-000"), is(true));
        assertThat(Formatador.CEP.estaFormatado("12345-67"), is(false));

        assertThat(Formatador.CEP.estaFormatado("047486"), is(false));
        assertThat(Formatador.CEP.estaFormatado(""), is(false));
        try {
            Formatador.CEP.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CEP.podeSerFormatado("12345-678"), is(false));
        assertThat(Formatador.CEP.podeSerFormatado("12345678"), is(true));
        assertThat(Formatador.CEP.podeSerFormatado("020"), is(false));
        assertThat(Formatador.CEP.podeSerFormatado(""), is(false));
        assertThat(Formatador.CEP.podeSerFormatado(null), is(false));
    }

    private void assertThrowsFormat(String valor) {
        try {
            Formatador.CEP.formata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    private void assertThrowsDesformat(String valor) {
        try {
            Formatador.CEP.desformata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }
}
