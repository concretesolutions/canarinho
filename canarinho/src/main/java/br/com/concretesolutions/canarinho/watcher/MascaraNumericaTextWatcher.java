package br.com.concretesolutions.canarinho.watcher;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;

import java.util.Arrays;

import br.com.concretesolutions.canarinho.formatador.Formatador;
import br.com.concretesolutions.canarinho.validator.Validador;
import br.com.concretesolutions.canarinho.watcher.evento.EventoDeValidacao;

/**
 * Máscara c/ validação genérica para campos numéricos.
 *
 * @see br.com.concretesolutions.canarinho.watcher.MascaraNumericaTextWatcher.Builder
 */
public final class MascaraNumericaTextWatcher implements TextWatcher {

    private final Validador.ResultadoParcial resultadoParcial = new Validador.ResultadoParcial();
    private final Validador validador;
    private final EventoDeValidacao callbackErros;
    private final char[] mascara;
    private final InputFilter[] filtroNumerico;

    private boolean mudancaInterna = false;
    private int tamanhoAnterior = 0;

    private MascaraNumericaTextWatcher(Builder builder) {
        this.validador = builder.validador;
        this.callbackErros = builder.callbackErros;
        this.mascara = builder.mascara.toCharArray();
        final int length = mascara.length;
        this.filtroNumerico = new InputFilter[]{new InputFilter.LengthFilter(length)};
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // não faz nada
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // não faz nada
    }

    @Override
    public void afterTextChanged(Editable s) {

        // retorna se a mudança foi disparada pelo método atualizaTexto
        if (mudancaInterna)
            return;

        final boolean apagou = tamanhoAnterior > s.length();

        // Trata deleção e adição de forma diferente (só formata em adições)
        final StringBuilder builder = apagou ? trataRemocaoDeCaracter(s) : trataAdicaoDeCaracter(s);

        tamanhoAnterior = builder.length();
        atualizaTexto(s, builder);
    }

    // Usa o Editable para atualizar o Editable
    // Inspirado no código do Android: android.telephony.PhoneNumberFormattingTextWatcher
    // O cursor SEMPRE sera posicionado no final do conteúdo
    private void atualizaTexto(Editable s, StringBuilder builder) {

        mudancaInterna = true;
        s.replace(0, s.length(), builder, 0, builder.length());

        // The text could be changed by other TextWatcher after we changed it. If we found the
        // text is not the one we were expecting, just give up calling setSelection().
        if (builder.toString().equals(s.toString()))
            // TODO: estudar implantar a manutenção da posição do cursor
            Selection.setSelection(s, builder.length());

        // Atualiza as validações com o valor atual do Editable.
        efetuaValidacao(s);

        mudancaInterna = false;
    }

    private void efetuaValidacao(Editable s) {
        validador.ehValido(s, resultadoParcial);

        if (callbackErros == null)
            return;

        if (!resultadoParcial.isParcialmenteValido())
            callbackErros.invalido(s.toString(), resultadoParcial.getMensagem());

        else if (!resultadoParcial.isValido())
            callbackErros.parcialmenteValido(s.toString());

        else
            callbackErros.totalmenteValido(s.toString());
    }

    private StringBuilder trataAdicaoDeCaracter(Editable s) {

        // Garante que os filtros de tamanho estão aplicados
        verificaFiltro(s);
        final StringBuilder builder = new StringBuilder();
        final String str = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(s).replaceAll("");

        int j = 0; // Acompanha a posição nos dígitos

        // É recomendado não usar enhanced for em Android
        for (int i = 0; i < mascara.length; i++) {

            final char charMascara = mascara[i];

            if (charMascara != '#') { // '#' -> caracter de formatação
                builder.append(charMascara);
                continue;
            }

            if (j >= str.length())
                break;

            builder.append(str.charAt(j));
            j++;
        }

        return builder;
    }

    // Só é chamado após uma deleção, portanto, é seguro chamar mascara[s.length()]
    private StringBuilder trataRemocaoDeCaracter(Editable s) {

        final StringBuilder builder = new StringBuilder(s);

        final boolean ultimoCaracterEraMascara = mascara[s.length()] != '#';

        // Se o caracter a frente do último atual era de formatação, apaga o último atual
        if (ultimoCaracterEraMascara)
            builder.deleteCharAt(builder.length() - 1);

        // Caso haja mais de um caracter de formatação (da máscara) faz um loop
        // até chegar em um caracter que não seja de formatação
        while (builder.length() > 0 && mascara[builder.length() - 1] != '#')
            builder.deleteCharAt(builder.length() - 1);

        return builder;
    }

    private void verificaFiltro(final Editable s) {
        // Filtro de tamanho
        if (!Arrays.equals(s.getFilters(), filtroNumerico))
            s.setFilters(filtroNumerico);
    }

    /**
     * Builder para construção de máscaras que validam.
     */
    public static final class Builder {

        private Validador validador;
        private EventoDeValidacao callbackErros;
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
            this.callbackErros = callbackErros;
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

            if (mascara == null || mascara.isEmpty() || !mascara.contains("#"))
                throw new IllegalArgumentException("Máscara precisa conter ao menos um caracter '#'");

            if (validador == null || callbackErros == null)
                throw new IllegalArgumentException("Por favor, defina um validador e um callback de erros.");

            return new MascaraNumericaTextWatcher(this);
        }
    }
}
