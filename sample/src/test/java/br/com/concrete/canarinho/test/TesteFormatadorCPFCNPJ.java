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
public class TesteFormatadorCPFCNPJ {

    @Test
    public void consegueFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.Constantes.CPF_CNPJ.formata("50.713.534/0001-33"), is("50.713.534/0001-33"));
        assertThat(Formatador.Constantes.CPF_CNPJ.formata("50713534000133"), is("50.713.534/0001-33"));

        assertThat(Formatador.Constantes.CPF_CNPJ.formata("72.606.598/0001-78"), is("72.606.598/0001-78"));
        assertThat(Formatador.Constantes.CPF_CNPJ.formata("72606598000178"), is("72.606.598/0001-78"));

        assertThat(Formatador.Constantes.CPF_CNPJ.formata("23.106.535/0001-47"), is("23.106.535/0001-47"));
        assertThat(Formatador.Constantes.CPF_CNPJ.formata("23106535000147"), is("23.106.535/0001-47"));

        assertThrowsFormat("");
        assertThrowsFormat("123123");
        assertThrowsFormat("23.106.535/0001  -   47");
        assertThrowsFormat("       23.106.535/0001-47      ");

        // Gerados automaticamente para testes
        assertThat(Formatador.Constantes.CPF_CNPJ.formata("545.586.262-66"), is("545.586.262-66"));
        assertThat(Formatador.Constantes.CPF_CNPJ.formata("54558626266"), is("545.586.262-66"));

        assertThat(Formatador.Constantes.CPF_CNPJ.formata("020.724.833-87"), is("020.724.833-87"));
        assertThat(Formatador.Constantes.CPF_CNPJ.formata("02072483387"), is("020.724.833-87"));

        assertThat(Formatador.Constantes.CPF_CNPJ.formata("188.527.045-31"), is("188.527.045-31"));
        assertThat(Formatador.Constantes.CPF_CNPJ.formata("18852704531"), is("188.527.045-31"));

        assertThat(Formatador.Constantes.CPF_CNPJ.formata("047.486.777-32"), is("047.486.777-32"));
        assertThat(Formatador.Constantes.CPF_CNPJ.formata("04748677732"), is("047.486.777-32"));

        assertThrowsFormat("");
        assertThrowsFormat("123123");
        assertThrowsFormat("123.123.123    -12");
        assertThrowsFormat("       047.486.777-32      ");
    }

    @Test
    public void consegueDesformatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("50.713.534/0001-33"), is("50713534000133"));
        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("50713534000133"), is("50713534000133"));

        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("72.606.598/0001-78"), is("72606598000178"));
        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("72606598000178"), is("72606598000178"));

        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("23.106.535/0001-47"), is("23106535000147"));
        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("23106535000147"), is("23106535000147"));

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
        assertThrowsDesformat("123.123.123    -12");
        assertThrowsDesformat("       047.486.777-32      ");

        // Gerados automaticamente para testes
        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("545.586.262-66"), is("54558626266"));
        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("54558626266"), is("54558626266"));

        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("020.724.833-87"), is("02072483387"));
        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("02072483387"), is("02072483387"));

        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("188.527.045-31"), is("18852704531"));
        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("18852704531"), is("18852704531"));

        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("047.486.777-32"), is("04748677732"));
        assertThat(Formatador.Constantes.CPF_CNPJ.desformata("04748677732"), is("04748677732"));

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
        assertThrowsDesformat("123.123.123    -12");
        assertThrowsDesformat("       047.486.777-32      ");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        // Gerados automaticamente para testes
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("72.606.598/0001-78"), is(true));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("72606598000178"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("23.106.535/0001-47"), is(true));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("23106535000147"), is(false));

        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("047486"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado(""), is(false));
        try {
            Formatador.Constantes.CPF_CNPJ.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }

        // Gerados automaticamente para testes
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("545.586.262-66"), is(true));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("54558626266"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("020.724.833-87"), is(true));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("02072483387"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("188.527.045-31"), is(true));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("18852704531"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("047.486.777-32"), is(true));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("04748677732"), is(false));

        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado("047486"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.estaFormatado(""), is(false));
        try {
            Formatador.Constantes.CPF_CNPJ.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado("23.106.535/0001-47"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado("23106535000147"), is(true));
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado("020"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado(""), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado(null), is(false));

        // Gerados automaticamente para testes
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado("545.586.262-66"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado("54558626266"), is(true));
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado("020"), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado(""), is(false));
        assertThat(Formatador.Constantes.CPF_CNPJ.podeSerFormatado(null), is(false));
    }

    private void assertThrowsFormat(String valor) {
        try {
            Formatador.Constantes.CPF_CNPJ.formata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    private void assertThrowsDesformat(String valor) {
        try {
            Formatador.Constantes.CPF_CNPJ.desformata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }
}
