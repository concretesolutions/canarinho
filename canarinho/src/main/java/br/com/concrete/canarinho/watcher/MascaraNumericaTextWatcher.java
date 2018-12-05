package br.com.concrete.canarinho.watcher;

import android.text.Editable;
import android.text.InputFilter;

import java.util.Arrays;

import br.com.concrete.canarinho.validator.Validador;
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao;

/**
 * Máscara c/ validação genérica para campos numéricos.
 *
 * @see br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher.Builder
 */
public final class MascaraNumericaTextWatcher extends BaseCanarinhoTextWatcher {

    private final Validador.ResultadoParcial resultadoParcial = new Validador.ResultadoParcial();
    private final Validador validador;
    private final char[] mascara;
    private final InputFilter[] filtroNumerico;

    /**
     * Construtor para adicionar uma máscara sem validação.
     *
     * @param mascara Máscara para efetuar a formatação
     */
    public MascaraNumericaTextWatcher(String mascara) {
        this(new Builder().paraMascara(mascara));
    }

    private MascaraNumericaTextWatcher(Builder builder) {
        this.mascara = builder.mascara.toCharArray();
        this.validador = builder.validador;

        final int length = mascara.length;
        this.filtroNumerico = new InputFilter[]{new InputFilter.LengthFilter(length)};

        setEventoDeValidacao(builder.eventoDeValidacao);
    }

    @Override
    public void afterTextChanged(Editable s) {

        // retorna se a mudança foi disparada pelo método atualizaTexto
        if (isMudancaInterna()) {
            return;
        }

        // Filtro de tamanho
        if (!Arrays.equals(s.getFilters(), filtroNumerico)) {
            s.setFilters(filtroNumerico);
        }

        final StringBuilder builder = trataAdicaoRemocaoDeCaracter(s, mascara);

        atualizaTexto(validador, resultadoParcial, s, builder, false);
    }

    /**
     * Builder para construção de máscaras que validam.
     */
    public static final class Builder {

        private Validador validador;
        private EventoDeValidacao eventoDeValidacao;
        private String mascara;

        /**
         * O validador que será usado. Será chamada a implementação de
         * {@link Validador#ehValido(Editable, Validador.ResultadoParcial)}
         *
         * @param validador Implementação de {@link Validador}
         * @return this para interface fluente
         */
        public Builder comValidador(Validador validador) {
            this.validador = validador;
            return this;
        }

        /**
         * Para cada caracter digitado será validado de acordo com o Validador e o callback
         * correspondente ao resultado da validação será chamado para que a interface possa ser atualizada.
         *
         * @param callbackErros {@link EventoDeValidacao} que será chamado durante a validação
         * @return this para interface fluente
         */
        public Builder comCallbackDeValidacao(EventoDeValidacao callbackErros) {
            this.eventoDeValidacao = callbackErros;
            return this;
        }

        /**
         * A máscara só pode conter os caracteres '#' no lugar dos números. Assim, a máscara
         * '#####-##' irá aceitar apenas números no lugar de '#'. Ao digitar, o usuário irá ver:
         * '12345-67'.
         *
         * @param mascara Máscara
         * @return this para interface fluente
         */
        public Builder paraMascara(String mascara) {
            this.mascara = mascara;
            return this;
        }

        /**
         * Constrói a máscara.
         *
         * @return A instância imutável da máscara.
         */
        public final MascaraNumericaTextWatcher build() {

            if (mascara == null || mascara.isEmpty() || !mascara.contains("#")) {
                throw new IllegalArgumentException("Máscara precisa conter ao menos um caracter '#'");
            }

            return new MascaraNumericaTextWatcher(this);
        }
    }
}
