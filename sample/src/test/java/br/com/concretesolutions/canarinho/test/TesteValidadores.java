package br.com.concretesolutions.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import br.com.concretesolutions.canarinho.sample.BuildConfig;
import br.com.concretesolutions.canarinho.validator.Validador;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class TesteValidadores {

    @Test
    public void consegueValidarCPF() {

        // Gerados automaticamente para testes
        assertThat(Validador.CPF.ehValido("545.586.262-66"), is(true));
        assertThat(Validador.CPF.ehValido("655.274.921-02"), is(true));
        assertThat(Validador.CPF.ehValido("020.724.833-87"), is(true));
        assertThat(Validador.CPF.ehValido("188.527.045-31"), is(true));
        assertThat(Validador.CPF.ehValido("047.486.777-32"), is(true));

        assertThat(Validador.CPF.ehValido("54558626266"), is(true));
        assertThat(Validador.CPF.ehValido("65527492102"), is(true));
        assertThat(Validador.CPF.ehValido("02072483387"), is(true));
        assertThat(Validador.CPF.ehValido("18852704531"), is(true));
        assertThat(Validador.CPF.ehValido("04748677732"), is(true));

        assertThat(Validador.CPF.ehValido("545.111.262-66"), is(false));
        assertThat(Validador.CPF.ehValido("655.111.921-02"), is(false));
        assertThat(Validador.CPF.ehValido("020.111.833-87"), is(false));
        assertThat(Validador.CPF.ehValido("188.111.045-31"), is(false));
        assertThat(Validador.CPF.ehValido("047.111.777-32"), is(false));

        assertThat(Validador.CPF.ehValido("54111626266"), is(false));
        assertThat(Validador.CPF.ehValido("65111492102"), is(false));
        assertThat(Validador.CPF.ehValido("02111483387"), is(false));
        assertThat(Validador.CPF.ehValido("18111704531"), is(false));
        assertThat(Validador.CPF.ehValido("04111677732"), is(false));
    }

    @Test
    public void consegueValidarCNPJ() {

        // Gerados automaticamente para testes
        assertThat(Validador.CNPJ.ehValido("50.713.534/0001-33"), is(true));
        assertThat(Validador.CNPJ.ehValido("77.135.038/0001-04"), is(true));
        assertThat(Validador.CNPJ.ehValido("58.613.246/0001-19"), is(true));
        assertThat(Validador.CNPJ.ehValido("50713534000133"), is(true));
        assertThat(Validador.CNPJ.ehValido("77135038000104"), is(true));

        // Gerados automaticamente para testes
        assertThat(Validador.CNPJ.ehValido("50.713.111/0001-33"), is(false));
        assertThat(Validador.CNPJ.ehValido("77.135.111/0001-04"), is(false));
        assertThat(Validador.CNPJ.ehValido("50713531110133"), is(false));
        assertThat(Validador.CNPJ.ehValido("77135031110104"), is(false));
    }

    @Test
    public void consegueValidarBoletoNormal() {

        // boleto bradesco
        assertThat(Validador.BOLETO
                .ehValido("23790.12301 60000.000053 25000.456704 9 64680000013580"), is(true));

        // boleto bradesco
        assertThat(Validador.BOLETO
                .ehValido("23790123016000000005325000456704964680000013580"), is(true));

        // boleto banco do brasil
        assertThat(Validador.BOLETO
                .ehValido("00199.38414 90480.002550 84666.970219 4 64290000007726"), is(true));


        // Boleto itau desformatado
        assertThat(Validador.BOLETO
                .ehValido("34191750090000159091820521070001664890002370000"), is(true));

        // Boleto itau
        assertThat(Validador.BOLETO
                .ehValido("34191.75009 00001.590918 20521.070001 6 64890002370000"), is(true));

        // boleto Banestes
        assertThat(Validador.BOLETO
                .ehValido("02190.00015 05000.000017 23452.021696 2 25230000034000"), is(true));

        // boleto Caixa
        assertThat(Validador.BOLETO
                .ehValido("10491.44338 55119.000002 00000.000141 3 25230000093423"), is(true));

        // boleto Sudameris
        assertThat(Validador.BOLETO
                .ehValido("34790.12001 12345.695006 10502.000002 5 25230000034000"), is(true));
    }

    @Test
    public void consegueValidarBoletoTributoSemSerTaxa() {

        assertThat(Validador.BOLETO
                .ehValido("848600000015 523301622010 506101307129 620012111220"), is(true));
    }

    @Test
    public void consegueValidarBoletoTributoDeTaxa() {

        assertThat(Validador.BOLETO
                .ehValido("836600000019 078800481000 998854924516 001265611135"), is(true));

        assertThat(Validador.BOLETO
                .ehValido("826100000007 265400971429 620232390612 725103150621"), is(true));
    }

    @Test
    public void consegueValidarTelefone() {
        assertThat(Validador.TELFONE.ehValido("1112345678"), is(true));
        assertThat(Validador.TELFONE.ehValido("11123456789"), is(true));
        assertThat(Validador.TELFONE.ehValido("(11) 12345-6789"), is(true));
        assertThat(Validador.TELFONE.ehValido("111234567890"), is(false));
        assertThat(Validador.TELFONE.ehValido("1112345"), is(false));
    }

    @Test
    public void consegueValidarCEP() {
        assertThat(Validador.CEP.ehValido("12345678"), is(true));
        assertThat(Validador.CEP.ehValido("12345-678"), is(true));
        assertThat(Validador.CEP.ehValido("12345-67"), is(false));
        assertThat(Validador.CEP.ehValido("1234-678"), is(false));
        assertThat(Validador.CEP.ehValido(""), is(false));
    }
}
