package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorCPFCNPJ {

    @Test
    public void consegueFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF_CNPJ.formata("50.713.534/0001-33")).isEqualTo("50.713.534/0001-33");
        assertThat(Formatador.CPF_CNPJ.formata("50713534000133")).isEqualTo("50.713.534/0001-33");

        assertThat(Formatador.CPF_CNPJ.formata("72.606.598/0001-78")).isEqualTo("72.606.598/0001-78");
        assertThat(Formatador.CPF_CNPJ.formata("72606598000178")).isEqualTo("72.606.598/0001-78");

        assertThat(Formatador.CPF_CNPJ.formata("23.106.535/0001-47")).isEqualTo("23.106.535/0001-47");
        assertThat(Formatador.CPF_CNPJ.formata("23106535000147")).isEqualTo("23.106.535/0001-47");

        assertThrowsFormat("");
        assertThrowsFormat("123123");
        assertThrowsFormat("23.106.535/0001  -   47");
        assertThrowsFormat("       23.106.535/0001-47      ");

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF_CNPJ.formata("545.586.262-66")).isEqualTo("545.586.262-66");
        assertThat(Formatador.CPF_CNPJ.formata("54558626266")).isEqualTo("545.586.262-66");

        assertThat(Formatador.CPF_CNPJ.formata("020.724.833-87")).isEqualTo("020.724.833-87");
        assertThat(Formatador.CPF_CNPJ.formata("02072483387")).isEqualTo("020.724.833-87");

        assertThat(Formatador.CPF_CNPJ.formata("188.527.045-31")).isEqualTo("188.527.045-31");
        assertThat(Formatador.CPF_CNPJ.formata("18852704531")).isEqualTo("188.527.045-31");

        assertThat(Formatador.CPF_CNPJ.formata("047.486.777-32")).isEqualTo("047.486.777-32");
        assertThat(Formatador.CPF_CNPJ.formata("04748677732")).isEqualTo("047.486.777-32");

        assertThrowsFormat("");
        assertThrowsFormat("123123");
        assertThrowsFormat("123.123.123    -12");
        assertThrowsFormat("       047.486.777-32      ");
    }

    @Test
    public void consegueDesformatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF_CNPJ.desformata("50.713.534/0001-33")).isEqualTo("50713534000133");
        assertThat(Formatador.CPF_CNPJ.desformata("50713534000133")).isEqualTo("50713534000133");

        assertThat(Formatador.CPF_CNPJ.desformata("72.606.598/0001-78")).isEqualTo("72606598000178");
        assertThat(Formatador.CPF_CNPJ.desformata("72606598000178")).isEqualTo("72606598000178");

        assertThat(Formatador.CPF_CNPJ.desformata("23.106.535/0001-47")).isEqualTo("23106535000147");
        assertThat(Formatador.CPF_CNPJ.desformata("23106535000147")).isEqualTo("23106535000147");

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
        assertThrowsDesformat("123.123.123    -12");
        assertThrowsDesformat("       047.486.777-32      ");

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF_CNPJ.desformata("545.586.262-66")).isEqualTo("54558626266");
        assertThat(Formatador.CPF_CNPJ.desformata("54558626266")).isEqualTo("54558626266");

        assertThat(Formatador.CPF_CNPJ.desformata("020.724.833-87")).isEqualTo("02072483387");
        assertThat(Formatador.CPF_CNPJ.desformata("02072483387")).isEqualTo("02072483387");

        assertThat(Formatador.CPF_CNPJ.desformata("188.527.045-31")).isEqualTo("18852704531");
        assertThat(Formatador.CPF_CNPJ.desformata("18852704531")).isEqualTo("18852704531");

        assertThat(Formatador.CPF_CNPJ.desformata("047.486.777-32")).isEqualTo("04748677732");
        assertThat(Formatador.CPF_CNPJ.desformata("04748677732")).isEqualTo("04748677732");

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
        assertThrowsDesformat("123.123.123    -12");
        assertThrowsDesformat("       047.486.777-32      ");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF_CNPJ.estaFormatado("72.606.598/0001-78")).isTrue();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("72606598000178")).isFalse();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("23.106.535/0001-47")).isTrue();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("23106535000147")).isFalse();

        assertThat(Formatador.CPF_CNPJ.estaFormatado("047486")).isFalse();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("")).isFalse();
        try {
            Formatador.CPF_CNPJ.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF_CNPJ.estaFormatado("545.586.262-66")).isTrue();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("54558626266")).isFalse();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("020.724.833-87")).isTrue();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("02072483387")).isFalse();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("188.527.045-31")).isTrue();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("18852704531")).isFalse();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("047.486.777-32")).isTrue();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("04748677732")).isFalse();

        assertThat(Formatador.CPF_CNPJ.estaFormatado("047486")).isFalse();
        assertThat(Formatador.CPF_CNPJ.estaFormatado("")).isFalse();
        try {
            Formatador.CPF_CNPJ.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado("23.106.535/0001-47")).isFalse();
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado("23106535000147")).isTrue();
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado("020")).isFalse();
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado("")).isFalse();
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado(null)).isFalse();

        // Gerados automaticamente para testes
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado("545.586.262-66")).isFalse();
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado("54558626266")).isTrue();
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado("020")).isFalse();
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado("")).isFalse();
        assertThat(Formatador.CPF_CNPJ.podeSerFormatado(null)).isFalse();
    }

    private void assertThrowsFormat(String valor) {
        try {
            Formatador.CPF_CNPJ.formata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    private void assertThrowsDesformat(String valor) {
        try {
            Formatador.CPF_CNPJ.desformata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }
}
