package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorCPF {

    @Test
    public void consegueFormatarCPF() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF.formata("545.586.262-66"), is("545.586.262-66"));
        assertThat(Formatador.CPF.formata("54558626266"), is("545.586.262-66"));

        assertThat(Formatador.CPF.formata("020.724.833-87"), is("020.724.833-87"));
        assertThat(Formatador.CPF.formata("02072483387"), is("020.724.833-87"));

        assertThat(Formatador.CPF.formata("188.527.045-31"), is("188.527.045-31"));
        assertThat(Formatador.CPF.formata("18852704531"), is("188.527.045-31"));

        assertThat(Formatador.CPF.formata("047.486.777-32"), is("047.486.777-32"));
        assertThat(Formatador.CPF.formata("04748677732"), is("047.486.777-32"));

        assertThrowsFormat("");
        assertThrowsFormat("123123");
        assertThrowsFormat("123.123.123    -12");
        assertThrowsFormat("       047.486.777-32      ");
    }

    @Test
    public void consegueDesformatarCPF() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF.desformata("545.586.262-66"), is("54558626266"));
        assertThat(Formatador.CPF.desformata("54558626266"), is("54558626266"));

        assertThat(Formatador.CPF.desformata("020.724.833-87"), is("02072483387"));
        assertThat(Formatador.CPF.desformata("02072483387"), is("02072483387"));

        assertThat(Formatador.CPF.desformata("188.527.045-31"), is("18852704531"));
        assertThat(Formatador.CPF.desformata("18852704531"), is("18852704531"));

        assertThat(Formatador.CPF.desformata("047.486.777-32"), is("04748677732"));
        assertThat(Formatador.CPF.desformata("04748677732"), is("04748677732"));

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
        assertThrowsDesformat("123.123.123    -12");
        assertThrowsDesformat("       047.486.777-32      ");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF.estaFormatado("545.586.262-66"), is(true));
        assertThat(Formatador.CPF.estaFormatado("54558626266"), is(false));
        assertThat(Formatador.CPF.estaFormatado("020.724.833-87"), is(true));
        assertThat(Formatador.CPF.estaFormatado("02072483387"), is(false));
        assertThat(Formatador.CPF.estaFormatado("188.527.045-31"), is(true));
        assertThat(Formatador.CPF.estaFormatado("18852704531"), is(false));
        assertThat(Formatador.CPF.estaFormatado("047.486.777-32"), is(true));
        assertThat(Formatador.CPF.estaFormatado("04748677732"), is(false));

        assertThat(Formatador.CPF.estaFormatado("047486"), is(false));
        assertThat(Formatador.CPF.estaFormatado(""), is(false));
        try {
            Formatador.CPF.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF.podeSerFormatado("545.586.262-66"), is(false));
        assertThat(Formatador.CPF.podeSerFormatado("54558626266"), is(true));
        assertThat(Formatador.CPF.podeSerFormatado("020"), is(false));
        assertThat(Formatador.CPF.podeSerFormatado(""), is(false));
        assertThat(Formatador.CPF.podeSerFormatado(null), is(false));
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
