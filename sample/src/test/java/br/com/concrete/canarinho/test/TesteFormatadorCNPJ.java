package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorCNPJ {

    @Test
    public void consegueFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CNPJ.formata("50.713.534/0001-33")).isEqualTo("50.713.534/0001-33");
        assertThat(Formatador.CNPJ.formata("50713534000133")).isEqualTo("50.713.534/0001-33");

        assertThat(Formatador.CNPJ.formata("72.606.598/0001-78")).isEqualTo("72.606.598/0001-78");
        assertThat(Formatador.CNPJ.formata("72606598000178")).isEqualTo("72.606.598/0001-78");

        assertThat(Formatador.CNPJ.formata("23.106.535/0001-47")).isEqualTo("23.106.535/0001-47");
        assertThat(Formatador.CNPJ.formata("23106535000147")).isEqualTo("23.106.535/0001-47");

        assertThrowsFormat("");
        assertThrowsFormat("123123");
        assertThrowsFormat("23.106.535/0001  -   47");
        assertThrowsFormat("       23.106.535/0001-47      ");
    }

    @Test
    public void consegueDesformatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CNPJ.desformata("50.713.534/0001-33")).isEqualTo("50713534000133");
        assertThat(Formatador.CNPJ.desformata("50713534000133")).isEqualTo("50713534000133");

        assertThat(Formatador.CNPJ.desformata("72.606.598/0001-78")).isEqualTo("72606598000178");
        assertThat(Formatador.CNPJ.desformata("72606598000178")).isEqualTo("72606598000178");

        assertThat(Formatador.CNPJ.desformata("23.106.535/0001-47")).isEqualTo("23106535000147");
        assertThat(Formatador.CNPJ.desformata("23106535000147")).isEqualTo("23106535000147");

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
        assertThrowsDesformat("123.123.123    -12");
        assertThrowsDesformat("       047.486.777-32      ");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CNPJ.estaFormatado("72.606.598/0001-78")).isTrue();
        assertThat(Formatador.CNPJ.estaFormatado("72606598000178")).isFalse();
        assertThat(Formatador.CNPJ.estaFormatado("23.106.535/0001-47")).isTrue();
        assertThat(Formatador.CNPJ.estaFormatado("23106535000147")).isFalse();

        assertThat(Formatador.CNPJ.estaFormatado("047486")).isFalse();
        assertThat(Formatador.CNPJ.estaFormatado("")).isFalse();
        try {
            Formatador.CNPJ.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CNPJ.podeSerFormatado("23.106.535/0001-47")).isFalse();
        assertThat(Formatador.CNPJ.podeSerFormatado("23106535000147")).isTrue();
        assertThat(Formatador.CNPJ.podeSerFormatado("020")).isFalse();
        assertThat(Formatador.CNPJ.podeSerFormatado("")).isFalse();
        assertThat(Formatador.CNPJ.podeSerFormatado(null)).isFalse();
    }

    private void assertThrowsFormat(String valor) {
        try {
            Formatador.CNPJ.formata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    private void assertThrowsDesformat(String valor) {
        try {
            Formatador.CNPJ.desformata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }
}
