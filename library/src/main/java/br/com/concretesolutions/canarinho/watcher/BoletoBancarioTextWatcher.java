package br.com.concretesolutions.canarinho.watcher;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;

import java.util.Arrays;

import br.com.concretesolutions.canarinho.validator.Validador;
import br.com.concretesolutions.canarinho.validator.ValidadorBoleto;
import br.com.concretesolutions.canarinho.watcher.evento.EventoDeValidacao;

/**
 * {@link TextWatcher} responsável por formatar e validar um {@link android.widget.EditText} para boletos.
 * Para usar este componente basta criar uma instância e chamar {@link android.widget.EditText#addTextChangedListener(TextWatcher)}.
 */
public final class BoletoBancarioTextWatcher implements TextWatcher {

    private static final char[] BOLETO_NORMAL = "#####.##### #####.###### #####.###### # ##############".toCharArray();
    private static final char[] BOLETO_TRIBUTO = "############ ############ ############ ############".toCharArray();
    private static final InputFilter[] FILTRO_TRIBUTO = new InputFilter[]{new InputFilter.LengthFilter(BOLETO_TRIBUTO.length)};
    private static final InputFilter[] FILTRO_NORMAL = new InputFilter[]{new InputFilter.LengthFilter(BOLETO_NORMAL.length)};

    private boolean mudancaInterna = false;
    private int tamanhoAnterior = 0;

    private final Validador validador = ValidadorBoleto.getInstance();
    private final Validador.ResultadoParcial resultadoParcial = new Validador.ResultadoParcial();
    private final EventoDeValidacao callbackErros;

    public BoletoBancarioTextWatcher(EventoDeValidacao callbackErros) {
        this.callbackErros = callbackErros;
    }

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Não faz nada aqui
    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        // Não faz nada aqui
    }

    @Override
    public final void afterTextChanged(Editable s) {

        // retorna se a String é menor que o mínimo de caracteres
        // para haver uma formatação ou se a mudança foi disparada
        // pelo método atualizaTexto
        if (mudancaInterna)
            return;


        // Trata o caso em que tudo é apagado em lote
        if (s.length() < 3) {
            resultadoParcial.mensagem(null).parcialmenteValido(false).totalmenteValido(false);
            if (callbackErros != null)
                callbackErros.parcialmenteValido("");
            return;
        }

        final boolean apagou = tamanhoAnterior > s.length();
        final boolean tributo = ehTributo(s);
        final char[] mascara = tributo ? BOLETO_TRIBUTO : BOLETO_NORMAL;

        // Trata deleção e adição de forma diferente (só formata em adições)
        final StringBuilder builder = apagou
                ? trataRemocaoDeCaracter(s, mascara)
                : trataAdicaoDeCaracter(s, tributo, mascara);

        tamanhoAnterior = builder.length();
        atualizaTexto(s, builder);
    }

    public Validador.ResultadoParcial getResultadoParcial() {
        return resultadoParcial;
    }

    // CUIDADO AO ATUALIZAR O Editable AQUI!!!
    private void efetuaValidacao(Editable s) {
        validador.ehValido(s, resultadoParcial);

        if (callbackErros == null)
            return;

        if (!resultadoParcial.isParcialmenteValido())
            callbackErros.invalido(s.toString(), resultadoParcial.getMensagem());

        else {

            if (!resultadoParcial.isValido())
                callbackErros.parcialmenteValido(s.toString());

            else
                callbackErros.totalmenteValido(s.toString());
        }
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

    private StringBuilder trataAdicaoDeCaracter(Editable s, boolean tributo, char[] mascara) {

        // Garante que os filtros de tamanho estão aplicados
        verificaFiltro(s, tributo);
        final StringBuilder builder = new StringBuilder();
        final String str = s.toString().replaceAll("[\\s.]", "");

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
    private StringBuilder trataRemocaoDeCaracter(Editable s, char[] mascara) {

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

    private void verificaFiltro(final Editable s, final boolean tributo) {
        // Filtro de tamanho
        if (tributo && !Arrays.equals(s.getFilters(), FILTRO_TRIBUTO))
            s.setFilters(FILTRO_TRIBUTO);

        else if (!tributo && !Arrays.equals(s.getFilters(), FILTRO_NORMAL))
            s.setFilters(FILTRO_NORMAL);
    }

    // Boletos iniciados com 8 são tributos ou de concessionárias
    private boolean ehTributo(Editable e) {
        return e.charAt(0) == '8';
    }
}
