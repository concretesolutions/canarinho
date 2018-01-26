package br.com.concrete.canarinho.validator;

import android.text.Editable;

import br.com.concrete.canarinho.DigitoPara;
import br.com.concrete.canarinho.formatador.Formatador;

/**
 * Implementação de @{link Validador} para CPF
 *
 * @see Validador
 */
public final class ValidadorCPF implements Validador {

    private static final DigitoPara DIGITO_PARA_CPF = new DigitoPara.Builder()
            .comMultiplicadoresDeAte(2, 11)
            .complementarAoModulo()
            .trocandoPorSeEncontrar("0", 10, 11)
            .build();

    // No instance creation
    private ValidadorCPF() {
    }

    static ValidadorCPF getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean ehValido(String value) {

        if (value == null || value.length() < 11) {
            return false;
        }

        final String desformatado = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(value).replaceAll("");

        if (desformatado.length() != 11) {
            return false;
        }

        if (estaNaListaNegra(desformatado)) {
            return false;
        }

        final String cpfSemDigito = desformatado.substring(0, desformatado.length() - 2);
        final String digitos = desformatado.substring(desformatado.length() - 2);

        final String dig1 = DIGITO_PARA_CPF.calcula(cpfSemDigito);
        final String dig2 = DIGITO_PARA_CPF.calcula(cpfSemDigito + dig1);

        return (dig1 + dig2).equals(digitos);
    }

    @Override
    public ResultadoParcial ehValido(Editable valor, ResultadoParcial resultadoParcial) {

        if (resultadoParcial == null || valor == null) {
            throw new IllegalArgumentException("Valores não podem ser nulos");
        }

        final String desformatado = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(valor).replaceAll("");

        if (!ehValido(desformatado)) {
            return resultadoParcial
                    .parcialmenteValido(desformatado.length() < 11)
                    .mensagem("CPF inválido")
                    .totalmenteValido(false);
        }

        return resultadoParcial
                .parcialmenteValido(true)
                .totalmenteValido(true);
    }

    // De acordo ao cálculo dos digitos verificadores, os CPFs abaixo são válidos, entretanto os mesmo
    // são considerados inválidos pela Receita Federal
    // 00000000000, 11111111111, 22222222222, 33333333333, 44444444444, 55555555555,
    // 66666666666, 77777777777, 88888888888, 99999999999, 12345678909
    private boolean estaNaListaNegra(String valor) {

        boolean igual = true;

        for (int i = 1; i < 11 && igual; i++) {
            if (valor.charAt(i) != valor.charAt(0)) {
                igual = false;
            }
        }

        return igual || valor.equals("12345678909");
    }

    private static class SingletonHolder {
        private static final ValidadorCPF INSTANCE = new ValidadorCPF();
    }
}