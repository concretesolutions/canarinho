package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorCPF {

    @Test
    public void consegueFormatarCPF() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF.formata("545.586.262-66")).isEqualTo("545.586.262-66");
        assertThat(Formatador.CPF.formata("54558626266")).isEqualTo("545.586.262-66");

        assertThat(Formatador.CPF.formata("020.724.833-87")).isEqualTo("020.724.833-87");
        assertThat(Formatador.CPF.formata("02072483387")).isEqualTo("020.724.833-87");

        assertThat(Formatador.CPF.formata("188.527.045-31")).isEqualTo("188.527.045-31");
        assertThat(Formatador.CPF.formata("18852704531")).isEqualTo("188.527.045-31");

        assertThat(Formatador.CPF.formata("047.486.777-32")).isEqualTo("047.486.777-32");
        assertThat(Formatador.CPF.formata("04748677732")).isEqualTo("047.486.777-32");

        assertThrowsFormat("");
        assertThrowsFormat("123123");
        assertThrowsFormat("123.123.123    -12");
        assertThrowsFormat("       047.486.777-32      ");
    }

    @Test
    public void consegueDesformatarCPF() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF.desformata("545.586.262-66")).isEqualTo("54558626266");
        assertThat(Formatador.CPF.desformata("54558626266")).isEqualTo("54558626266");

        assertThat(Formatador.CPF.desformata("020.724.833-87")).isEqualTo("02072483387");
        assertThat(Formatador.CPF.desformata("02072483387")).isEqualTo("02072483387");

        assertThat(Formatador.CPF.desformata("188.527.045-31")).isEqualTo("18852704531");
        assertThat(Formatador.CPF.desformata("18852704531")).isEqualTo("18852704531");

        assertThat(Formatador.CPF.desformata("047.486.777-32")).isEqualTo("04748677732");
        assertThat(Formatador.CPF.desformata("04748677732")).isEqualTo("04748677732");

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
        assertThrowsDesformat("123.123.123    -12");
        assertThrowsDesformat("       047.486.777-32      ");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF.estaFormatado("545.586.262-66")).isTrue();
        assertThat(Formatador.CPF.estaFormatado("54558626266")).isFalse();
        assertThat(Formatador.CPF.estaFormatado("020.724.833-87")).isTrue();
        assertThat(Formatador.CPF.estaFormatado("02072483387")).isFalse();
        assertThat(Formatador.CPF.estaFormatado("188.527.045-31")).isTrue();
        assertThat(Formatador.CPF.estaFormatado("18852704531")).isFalse();
        assertThat(Formatador.CPF.estaFormatado("047.486.777-32")).isTrue();
        assertThat(Formatador.CPF.estaFormatado("04748677732")).isFalse();

        assertThat(Formatador.CPF.estaFormatado("047486")).isFalse();
        assertThat(Formatador.CPF.estaFormatado("")).isFalse();
        try {
            Formatador.CPF.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF.podeSerFormatado("545.586.262-66")).isFalse();
        assertThat(Formatador.CPF.podeSerFormatado("54558626266")).isTrue();
        assertThat(Formatador.CPF.podeSerFormatado("020")).isFalse();
        assertThat(Formatador.CPF.podeSerFormatado("")).isFalse();
        assertThat(Formatador.CPF.podeSerFormatado(null)).isFalse();
    }

    private void assertThrowsFormat(String valor) {
        try {
            Formatador.CPF.formata(valor);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    private void assertThrowsDesformat(String valor) {
        try {
            Formatador.CPF.desformata(valor);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }
}
