package br.com.concrete.canarinho.validator;

import android.text.Editable;

import br.com.concrete.canarinho.formatador.Formatador;

/**
 * Implementação de @{link Validador} para CEP (Código de endereçamento Postal)
 *
 * @see Validador
 */
public final class ValidadorCEP implements Validador {

    // No instance creation
    private ValidadorCEP() {
    }

    public static ValidadorCEP getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean ehValido(String valor) {

        if (valor == null || valor.length() < 8) {
            return false;
        }

        final String desformatado = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(valor).replaceAll("");

        return desformatado.length() == 8;
    }

    @Override
    public ResultadoParcial ehValido(Editable valor, ResultadoParcial resultadoParcial) {

        if (resultadoParcial == null || valor == null) {
            throw new IllegalArgumentException("Valores não podem ser nulos");
        }

        final String desformatado = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(valor).replaceAll("");

        if (!ehValido(desformatado)) {
            return resultadoParcial
                    .parcialmenteValido(desformatado.length() < 8)
                    .mensagem("CEP inválido")
                    .totalmenteValido(false);
        }

        return resultadoParcial
                .parcialmenteValido(true)
                .totalmenteValido(true);
    }

    private static class SingletonHolder {
        private static final ValidadorCEP INSTANCE = new ValidadorCEP();
    }
}
