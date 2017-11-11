package br.com.concrete.canarinho.formatador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilitária para a implementação de todos os formatadores que precisam apenas da aplicação
 * de {@link Pattern}s.
 */
final class FormatadorBase implements Formatador {

    private final Pattern formatted;

    private final String formattedReplacement;

    private final Pattern unformatted;

    private final String unformattedReplacement;

    /**
     * Constrói um formatador que recebe a configuração de formatação e substituição.
     *
     * @param formatted              descrever conteúdo
     * @param formattedReplacement   descrever conteúdo
     * @param unformatted            descrever conteúdo
     * @param unformattedReplacement descrever conteúdo
     */
    FormatadorBase(
            Pattern formatted,
            String formattedReplacement,
            Pattern unformatted,
            String unformattedReplacement) {

        this.formatted = formatted;
        this.formattedReplacement = formattedReplacement;
        this.unformatted = unformatted;
        this.unformattedReplacement = unformattedReplacement;
    }

    @Override
    public final String formata(String value) throws IllegalArgumentException {

        if (value == null) {
            throw new IllegalArgumentException("Value may not be null.");
        }

        if (formatted.matcher(value).matches()) {
            return value;
        }

        return matchAndReplace(unformatted.matcher(value), formattedReplacement);
    }

    @Override
    public final String desformata(String value) throws IllegalArgumentException {

        if (value == null) {
            throw new IllegalArgumentException("Value may not be null.");
        }

        if (unformatted.matcher(value).matches()) {
            return value;
        }

        final Matcher matcher = formatted.matcher(value);
        return matchAndReplace(matcher, unformattedReplacement);
    }

    @Override
    public final boolean estaFormatado(String value) {

        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }

        return formatted.matcher(value).matches();
    }

    @Override
    public final boolean podeSerFormatado(String value) {
        return value != null && unformatted.matcher(value).matches();
    }

    private String matchAndReplace(Matcher matcher, String replacement) {

        if (matcher.matches()) {
            return matcher.replaceAll(replacement);
        }

        throw new IllegalArgumentException("Valor não está formatado propriamente.");
    }
}
