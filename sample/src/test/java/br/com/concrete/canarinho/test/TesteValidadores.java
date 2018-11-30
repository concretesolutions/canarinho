package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.concrete.canarinho.validator.Validador;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
public class TesteValidadores {

    @Test
    public void consegueValidarCPF() {

        // Gerados automaticamente para testes
        assertThat(Validador.CPF.ehValido("545.586.262-66")).isTrue();
        assertThat(Validador.CPF.ehValido("655.274.921-02")).isTrue();
        assertThat(Validador.CPF.ehValido("020.724.833-87")).isTrue();
        assertThat(Validador.CPF.ehValido("188.527.045-31")).isTrue();
        assertThat(Validador.CPF.ehValido("047.486.777-32")).isTrue();

        assertThat(Validador.CPF.ehValido("54558626266")).isTrue();
        assertThat(Validador.CPF.ehValido("65527492102")).isTrue();
        assertThat(Validador.CPF.ehValido("02072483387")).isTrue();
        assertThat(Validador.CPF.ehValido("18852704531")).isTrue();
        assertThat(Validador.CPF.ehValido("04748677732")).isTrue();

        assertThat(Validador.CPF.ehValido("545.111.262-66")).isFalse();
        assertThat(Validador.CPF.ehValido("655.111.921-02")).isFalse();
        assertThat(Validador.CPF.ehValido("020.111.833-87")).isFalse();
        assertThat(Validador.CPF.ehValido("188.111.045-31")).isFalse();
        assertThat(Validador.CPF.ehValido("047.111.777-32")).isFalse();

        assertThat(Validador.CPF.ehValido("54111626266")).isFalse();
        assertThat(Validador.CPF.ehValido("65111492102")).isFalse();
        assertThat(Validador.CPF.ehValido("02111483387")).isFalse();
        assertThat(Validador.CPF.ehValido("18111704531")).isFalse();
        assertThat(Validador.CPF.ehValido("04111677732")).isFalse();
    }

    @Test
    public void consegueValidarCNPJ() {

        // Gerados automaticamente para testes
        assertThat(Validador.CNPJ.ehValido("50.713.534/0001-33")).isTrue();
        assertThat(Validador.CNPJ.ehValido("77.135.038/0001-04")).isTrue();
        assertThat(Validador.CNPJ.ehValido("58.613.246/0001-19")).isTrue();
        assertThat(Validador.CNPJ.ehValido("50713534000133")).isTrue();
        assertThat(Validador.CNPJ.ehValido("77135038000104")).isTrue();

        // Gerados automaticamente para testes
        assertThat(Validador.CNPJ.ehValido("50.713.111/0001-33")).isFalse();
        assertThat(Validador.CNPJ.ehValido("77.135.111/0001-04")).isFalse();
        assertThat(Validador.CNPJ.ehValido("50713531110133")).isFalse();
        assertThat(Validador.CNPJ.ehValido("77135031110104")).isFalse();
    }

    @Test
    public void consegueValidarBoletoNormal() {

        // boleto bradesco
        assertThat(Validador.BOLETO
                .ehValido("23790.12301 60000.000053 25000.456704 9 64680000013580")).isTrue();

        // boleto bradesco
        assertThat(Validador.BOLETO
                .ehValido("23790123016000000005325000456704964680000013580")).isTrue();

        // boleto banco do brasil
        assertThat(Validador.BOLETO
                .ehValido("00199.38414 90480.002550 84666.970219 4 64290000007726")).isTrue();


        // Boleto itau desformatado
        assertThat(Validador.BOLETO
                .ehValido("34191750090000159091820521070001664890002370000")).isTrue();

        // Boleto itau
        assertThat(Validador.BOLETO
                .ehValido("34191.75009 00001.590918 20521.070001 6 64890002370000")).isTrue();

        // boleto Banestes
        assertThat(Validador.BOLETO
                .ehValido("02190.00015 05000.000017 23452.021696 2 25230000034000")).isTrue();

        // boleto Caixa
        assertThat(Validador.BOLETO
                .ehValido("10491.44338 55119.000002 00000.000141 3 25230000093423")).isTrue();

        // boleto Sudameris
        assertThat(Validador.BOLETO
                .ehValido("34790.12001 12345.695006 10502.000002 5 25230000034000")).isTrue();
    }

    @Test
    public void consegueValidarBoletoTributoSemSerTaxa() {

        assertThat(Validador.BOLETO
                .ehValido("848600000015 523301622010 506101307129 620012111220")).isTrue();
    }

    @Test
    public void consegueValidarBoletoTributoDeTaxa() {

        assertThat(Validador.BOLETO
                .ehValido("836600000019 078800481000 998854924516 001265611135")).isTrue();

        assertThat(Validador.BOLETO
                .ehValido("826100000007 265400971429 620232390612 725103150621")).isTrue();
    }

    @Test
    public void consegueValidarTelefone() {
        assertThat(Validador.TELEFONE.ehValido("1112345678")).isTrue();
        assertThat(Validador.TELEFONE.ehValido("11123456789")).isTrue();
        assertThat(Validador.TELEFONE.ehValido("(11) 12345-6789")).isTrue();
        assertThat(Validador.TELEFONE.ehValido("111234567890")).isFalse();
        assertThat(Validador.TELEFONE.ehValido("1112345")).isFalse();
    }

    @Test
    public void consegueValidarCEP() {
        assertThat(Validador.CEP.ehValido("12345678")).isTrue();
        assertThat(Validador.CEP.ehValido("12345-678")).isTrue();
        assertThat(Validador.CEP.ehValido("12345-67")).isFalse();
        assertThat(Validador.CEP.ehValido("1234-678")).isFalse();
        assertThat(Validador.CEP.ehValido("")).isFalse();
    }
}
