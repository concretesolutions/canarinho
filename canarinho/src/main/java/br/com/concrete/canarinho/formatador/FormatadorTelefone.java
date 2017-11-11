package br.com.concrete.canarinho.formatador;

import java.util.regex.Pattern;

/**
 * Formata no padrão de telefone brasileiro: (99) 99999-9999 ou (99) 9999-9999.
 */
public final class FormatadorTelefone implements Formatador {

    private static final Pattern NOVE_DIGITOS_FORMATADO = Pattern.compile(
            "\\((\\d{2})\\)\\s(\\d{5})-(\\d{4})"
    );

    private static final Pattern NOVE_DIGITOS_DESFORMATADO = Pattern.compile(
            "(\\d{2})(\\d{5})(\\d{4})"
    );

    private static final Pattern OITO_DIGITOS_FORMATADO = Pattern.compile(
            "\\((\\d{2})\\)\\s(\\d{4})-(\\d{4})"
    );

    private static final Pattern OITO_DIGITOS_DESFORMATADO = Pattern.compile(
            "(\\d{2})(\\d{4})(\\d{4})"
    );

    private static final FormatadorBase FORMATADOR_NOVE_DIGITOS = new FormatadorBase(
            NOVE_DIGITOS_FORMATADO,
            "($1) $2-$3",
            NOVE_DIGITOS_DESFORMATADO,
            "$1$2$3"
    );

    private static final FormatadorBase FORMATADOR_OITO_DIGITOS = new FormatadorBase(
            OITO_DIGITOS_FORMATADO,
            "($1) $2-$3",
            OITO_DIGITOS_DESFORMATADO,
            "$1$2$3"
    );

    private FormatadorTelefone() {
    }

    static FormatadorTelefone getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String formata(String value) {
        if (ehNoveDigitos(value)) {
            return FORMATADOR_NOVE_DIGITOS.formata(value);
        }

        return FORMATADOR_OITO_DIGITOS.formata(value);
    }

    @Override
    public String desformata(String value) {
        if (ehNoveDigitos(value)) {
            return FORMATADOR_NOVE_DIGITOS.desformata(value);
        }

        return FORMATADOR_OITO_DIGITOS.desformata(value);
    }

    @Override
    public boolean estaFormatado(String value) {
        if (ehNoveDigitos(value)) {
            return FORMATADOR_NOVE_DIGITOS.estaFormatado(value);
        }

        return FORMATADOR_OITO_DIGITOS.estaFormatado(value);
    }

    @Override
    public boolean podeSerFormatado(String value) {
        if (ehNoveDigitos(value)) {
            return FORMATADOR_NOVE_DIGITOS.podeSerFormatado(value);
        }

        return FORMATADOR_OITO_DIGITOS.podeSerFormatado(value);
    }

    private boolean ehNoveDigitos(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }

        return Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(value)
                .replaceAll("")
                .length() > 10;
    }

    private static class SingletonHolder {
        private static final FormatadorTelefone INSTANCE = new FormatadorTelefone();
    }
}
