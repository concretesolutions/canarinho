package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorBOLETO {

    @Test
    public void consegueFormatar() {
        assertThat(Formatador.BOLETO.formata("12345123451234512345612345123456712345678901234"))
                .isEqualTo("12345.12345 12345.123456 12345.123456 7 12345678901234");

        assertThat(Formatador.BOLETO.formata("23790123016000000005325000456704364670000013580"))
                .isEqualTo("23790.12301 60000.000053 25000.456704 3 64670000013580");

        assertThat(Formatador.BOLETO.formata("812345678901812345678901812345678901812345678901"))
                .isEqualTo("812345678901 812345678901 812345678901 812345678901");

        assertThat(Formatador.BOLETO.formata("23790.12301 60000.000053 25000.456704 3 64670000013580"))
                .isEqualTo("23790.12301 60000.000053 25000.456704 3 64670000013580");

        assertThat(Formatador.BOLETO.formata("812345678901 812345678901 812345678901 812345678901"))
                .isEqualTo("812345678901 812345678901 812345678901 812345678901");
    }

    @Test
    public void consegueDesformatar() {

        assertThat(Formatador.BOLETO.desformata("12345.12345 12345.123456 12345.123456 7 12345678901234"))
                .isEqualTo("12345123451234512345612345123456712345678901234");

        assertThat(Formatador.BOLETO.desformata("23790.12301 60000.000053 25000.456704 3 64670000013580"))
                .isEqualTo("23790123016000000005325000456704364670000013580");

        assertThat(Formatador.BOLETO.desformata("812345678901 812345678901 812345678901 812345678901"))
                .isEqualTo("812345678901812345678901812345678901812345678901");

        assertThat(Formatador.BOLETO.desformata("23790123016000000005325000456704364670000013580"))
                .isEqualTo("23790123016000000005325000456704364670000013580");

        assertThat(Formatador.BOLETO.desformata("812345678901812345678901812345678901812345678901"))
                .isEqualTo("812345678901812345678901812345678901812345678901");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        assertThat(Formatador.BOLETO.estaFormatado("12345.12345 12345.123456 12345.123456 7 12345678901234")).isTrue();
        assertThat(Formatador.BOLETO.estaFormatado("23790.12301 60000.000053 25000.456704 3 64670000013580")).isTrue();

        assertThat(Formatador.BOLETO.estaFormatado("812345678901812345678901812345678901812345678901")).isFalse();
        assertThat(Formatador.BOLETO.estaFormatado("23790123016000000005325000456704364670000013580")).isFalse();
        assertThat(Formatador.BOLETO.estaFormatado("812345678901812345678901812345678901812345678901")).isFalse();
    }

    @Test
    public void consegueDizerSePodeFormatar() {
        // TODO
    }
}
