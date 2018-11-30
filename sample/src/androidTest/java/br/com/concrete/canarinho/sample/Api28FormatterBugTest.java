package br.com.concrete.canarinho.sample;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import br.com.concrete.canarinho.formatador.Formatador;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class Api28FormatterBugTest {

    @Test
    @SdkSuppress(minSdkVersion = 28)
    public void moneyFormatSuccessfulRunsOnApi28() {
        final String value = "1.000,00";
        assertEquals("1000.00", Formatador.VALOR.desformata(value));
    }

    @Test
    @SdkSuppress(maxSdkVersion = 27)
    public void moneyFormatSuccessfulRunsOnApi27() {
        final String value = "1.000,00";
        assertEquals("1000.00", Formatador.VALOR.desformata(value));
    }
}
