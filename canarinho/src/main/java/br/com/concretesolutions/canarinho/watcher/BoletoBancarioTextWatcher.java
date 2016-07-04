package br.com.concretesolutions.canarinho.watcher;

import android.text.Editable;
import android.text.InputFilter;

import java.util.Arrays;

import br.com.concretesolutions.canarinho.validator.Validador;
import br.com.concretesolutions.canarinho.validator.ValidadorBoleto;
import br.com.concretesolutions.canarinho.watcher.evento.EventoDeValidacao;
import br.com.concretesolutions.canarinho.watcher.evento.EventoDeValidacaoDeBoleto;

/**
 * {@link android.text.TextWatcher} responsável por formatar e validar um {@link
 * android.widget.EditText} para boletos. Para usar este componente basta criar uma instância e
 * chamar {@link android.widget.EditText#addTextChangedListener(android.text.TextWatcher)}.
 */
public final class BoletoBancarioTextWatcher extends BaseCanarinhoTextWatcher {

    private static final char[] BOLETO_NORMAL = "#####.##### #####.###### #####.###### # ##############".toCharArray();
    private static final char[] BOLETO_TRIBUTO = "############ ############ ############ ############".toCharArray();
    private static final InputFilter[] FILTRO_TRIBUTO = new InputFilter[]{
            new InputFilter.LengthFilter(BOLETO_TRIBUTO.length)};
    private static final InputFilter[] FILTRO_NORMAL = new InputFilter[]{
            new InputFilter.LengthFilter(BOLETO_NORMAL.length)};

    private final Validador validador = ValidadorBoleto.getInstance();
    private final Validador.ResultadoParcial resultadoParcial = new Validador.ResultadoParcial();

    /**
     * TODO Javadoc pendente
     *
     * @param callbackErros a descrever
     */
    public BoletoBancarioTextWatcher(EventoDeValidacao callbackErros) {
        setEventoDeValidacao(callbackErros);
    }

    @Override
    public final void afterTextChanged(Editable s) {

        // retorna se a String é menor que o mínimo de caracteres
        // para haver uma formatação ou se a mudança foi disparada
        // pelo método atualizaTexto
        if (isMudancaInterna()) {
            return;
        }

        // Trata o caso em que tudo é apagado em lote
        if (s.length() < 3) {
            resultadoParcial.mensagem(null).parcialmenteValido(false).totalmenteValido(false);
            if (getEventoDeValidacao() != null) {
                getEventoDeValidacao().parcialmenteValido("");
            }
        }

        if (s.length() == 0) {
            verificaFiltro(s, false);
            return;
        }

        final boolean tributo = ehTributo(s);
        final char[] mascara = tributo ? BOLETO_TRIBUTO : BOLETO_NORMAL;
        verificaFiltro(s, tributo);

        // Trata deleção e adição de forma diferente (só formata em adições)
        final StringBuilder builder = trataAdicaoRemocaoDeCaracter(s, mascara);
        atualizaTexto(validador, resultadoParcial, s, builder);
    }

    public Validador.ResultadoParcial getResultadoParcial() {
        return resultadoParcial;
    }

    @Override
    protected void efetuaValidacao(Validador validador, Validador.ResultadoParcial resultadoParcial, Editable s) {

        validador.ehValido(s, resultadoParcial);

        final EventoDeValidacao callbackErros = getEventoDeValidacao();

        if (callbackErros == null) {
            return;
        }

        final String valorAtual = s.toString();
        if (!resultadoParcial.isParcialmenteValido()) {

            final String mensagem = resultadoParcial.getMensagem();
            callbackErros.invalido(valorAtual, mensagem);

            if (callbackErros instanceof EventoDeValidacaoDeBoleto) {

                final int bloco;

                if (mensagem.startsWith("Primeiro")) {
                    bloco = 1;
                } else if (mensagem.startsWith("Segundo")) {
                    bloco = 2;
                } else if (mensagem.startsWith("Terceiro")) {
                    bloco = 3;
                } else if (mensagem.startsWith("Quarto")) {
                    bloco = 4;
                } else {
                    throw new IllegalArgumentException("Valor não reconhecido para bloco");
                }

                ((EventoDeValidacaoDeBoleto) callbackErros).invalido(valorAtual, bloco);
            }

        } else if (!resultadoParcial.isValido()) {
            callbackErros.parcialmenteValido(valorAtual);
        } else {
            callbackErros.totalmenteValido(valorAtual);
        }
    }

    private void verificaFiltro(final Editable s, final boolean tributo) {
        // Filtro de tamanho
        if (tributo && !Arrays.equals(s.getFilters(), FILTRO_TRIBUTO)) {
            s.setFilters(FILTRO_TRIBUTO);
        } else if (!tributo && !Arrays.equals(s.getFilters(), FILTRO_NORMAL)) {
            s.setFilters(FILTRO_NORMAL);
        }
    }

    // Boletos iniciados com 8 são tributos ou de concessionárias
    private boolean ehTributo(Editable e) {
        return e.charAt(0) == '8';
    }
}
