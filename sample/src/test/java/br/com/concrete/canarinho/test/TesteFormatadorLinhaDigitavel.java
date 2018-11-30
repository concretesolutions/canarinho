package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.formatador.Formatador;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
public class TesteFormatadorLinhaDigitavel {

    @Test
    public void consegueFormatarEDesformatar() {

        assertThat(Formatador.LINHA_DIGITAVEL.desformata("812345678901812345678901812345678901812345678901"))
                .isEqualTo("81234567890812345678908123456789081234567890");

        final String original = "23790123016000000005325000456704964680000013580";
        final String boleto = Formatador.LINHA_DIGITAVEL.desformata(original);
        final String linhaDigitavel = Formatador.LINHA_DIGITAVEL.formata(boleto);

        assertThat(linhaDigitavel).isEqualTo(original);
    }

    @Test
    public void consegueDizerSeEstaFormatado() {
        assertThat(Formatador.LINHA_DIGITAVEL.estaFormatado("81234567890812345678908123456789081234567890")).isFalse();
        assertThat(Formatador.LINHA_DIGITAVEL.estaFormatado("812345678901 812345678901 812345678901 812345678901")).isTrue();
        assertThat(Formatador.LINHA_DIGITAVEL.estaFormatado("23790.12301 60000.000053 25000.456704 9 64680000013580")).isTrue();
    }

    @Test
    public void consegueDizerSePodeFormatar() {
        assertThat(Formatador.LINHA_DIGITAVEL.podeSerFormatado("8123456789081234567890812345678908123456")).isFalse();
        assertThat(Formatador.LINHA_DIGITAVEL.podeSerFormatado("23790.1230 60000.00005 25000.45670 9 64680000013580")).isTrue();
    }
}
