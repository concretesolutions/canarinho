package br.com.concretesolutions.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import br.com.concretesolutions.canarinho.formatador.Formatador;
import br.com.concretesolutions.canarinho.sample.BuildConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class TesteFormatadorLinhaDigitavel {

    @Test
    public void consegueFormatarEDesformatar() {

        assertThat(Formatador.LINHA_DIGITAVEL.desformata("812345678901812345678901812345678901812345678901"),
                is("81234567890812345678908123456789081234567890"));

        final String original = "23790123016000000005325000456704964680000013580";
        final String boleto = Formatador.LINHA_DIGITAVEL.desformata(original);
        final String linhaDigitavel = Formatador.LINHA_DIGITAVEL.formata(boleto);

        assertThat(linhaDigitavel, equalTo(original));
    }

    @Test
    public void consegueDizerSeEstaFormatado() {
        assertThat(Formatador.LINHA_DIGITAVEL.estaFormatado("81234567890812345678908123456789081234567890"), is(false));
        assertThat(Formatador.LINHA_DIGITAVEL.estaFormatado("812345678901 812345678901 812345678901 812345678901"), is(true));
        assertThat(Formatador.LINHA_DIGITAVEL.estaFormatado("23790.12301 60000.000053 25000.456704 9 64680000013580"), is(true));
    }

    @Test
    public void consegueDizerSePodeFormatar() {
        assertThat(Formatador.LINHA_DIGITAVEL.podeSerFormatado("8123456789081234567890812345678908123456"), is(false));
        assertThat(Formatador.LINHA_DIGITAVEL.podeSerFormatado("23790.1230 60000.00005 25000.45670 9 64680000013580"), is(true));
    }
}
