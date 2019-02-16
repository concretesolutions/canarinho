package br.com.concrete.canarinho.validator;

import android.text.Editable;

import br.com.concrete.canarinho.DigitoPara;
import br.com.concrete.canarinho.formatador.Formatador;

/**
 * Implementação de @{link Validador} para CNPJ.
 *
 * @see Validador
 */
public final class ValidadorCNPJ implements Validador {

    private static final DigitoPara DIGITO_PARA_CNPJ = new DigitoPara.Builder()
            .complementarAoModulo()
            .trocandoPorSeEncontrar("0", 10, 11)
            .build();

    // No instance creation
    private ValidadorCNPJ() {
    }

    public static ValidadorCNPJ getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean ehValido(String value) {

        if (value == null || value.length() < 14) {
            return false;
        }

        final String desformatado = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(value).replaceAll("");

        if (desformatado.length() != 14) {
            return false;
        }

        final String cnpjSemDigitos = desformatado.substring(0, desformatado.length() - 2);
        final String digitos = desformatado.substring(desformatado.length() - 2);

        final String dig1 = DIGITO_PARA_CNPJ.calcula(cnpjSemDigitos);
        final String dig2 = DIGITO_PARA_CNPJ.calcula(cnpjSemDigitos + dig1);

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
                    .parcialmenteValido(desformatado.length() < 14)
                    .mensagem("CNPJ inválido")
                    .totalmenteValido(false);
        }

        return resultadoParcial
                .parcialmenteValido(true)
                .totalmenteValido(true);
    }

    private static class SingletonHolder {
        private static final ValidadorCNPJ INSTANCE = new ValidadorCNPJ();
    }
}