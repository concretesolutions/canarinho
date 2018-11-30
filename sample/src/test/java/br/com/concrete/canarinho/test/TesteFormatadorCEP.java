package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorCEP {

    @Test
    public void consegueFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CEP.formata("00000-000")).isEqualTo("00000-000");
        assertThat(Formatador.CEP.formata("00000000")).isEqualTo("00000-000");

        assertThat(Formatador.CEP.formata("12345-123")).isEqualTo("12345-123");
        assertThat(Formatador.CEP.formata("12345678")).isEqualTo("12345-678");

        assertThrowsFormat("");
        assertThrowsFormat("123123");
    }

    @Test
    public void consegueDesformatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CEP.desformata("00000-000")).isEqualTo("00000000");
        assertThat(Formatador.CEP.desformata("00000000")).isEqualTo("00000000");

        assertThat(Formatador.CEP.desformata("12345-123")).isEqualTo("12345123");
        assertThat(Formatador.CEP.desformata("12345678")).isEqualTo("12345678");

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CEP.estaFormatado("12345-678")).isTrue();
        assertThat(Formatador.CEP.estaFormatado("12345678")).isFalse();
        assertThat(Formatador.CEP.estaFormatado("00000-000")).isTrue();
        assertThat(Formatador.CEP.estaFormatado("12345-67")).isFalse();

        assertThat(Formatador.CEP.estaFormatado("047486")).isFalse();
        assertThat(Formatador.CEP.estaFormatado("")).isFalse();
        try {
            Formatador.CEP.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {
        // Gerados automaticamente para testes
        assertThat(Formatador.CEP.podeSerFormatado("12345-678")).isFalse();
        assertThat(Formatador.CEP.podeSerFormatado("12345678")).isTrue();
        assertThat(Formatador.CEP.podeSerFormatado("020")).isFalse();
        assertThat(Formatador.CEP.podeSerFormatado("")).isFalse();
        assertThat(Formatador.CEP.podeSerFormatado(null)).isFalse();
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
