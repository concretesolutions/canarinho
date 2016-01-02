package br.com.concretesolutions.canarinho.watcher;

import android.text.Editable;
import android.text.InputFilter;

import br.com.concretesolutions.canarinho.validator.Validador;
import br.com.concretesolutions.canarinho.validator.ValidadorCEP;
import br.com.concretesolutions.canarinho.watcher.evento.EventoDeValidacao;

/**
 * {@link android.text.TextWatcher} responsável por formatar e validar um {@link android.widget.EditText} para CEPs.
 * Para usar este componente basta criar uma instância e chamar
 * {@link android.widget.EditText#addTextChangedListener(android.text.TextWatcher)}.
 */
public final class CEPTextWatcher extends BaseCanarinhoTextWatcher {

    private static final char[] CEP_DIGITOS = "#####-###".toCharArray();

    private static final InputFilter[] FILTRO_OITO_DIGITOS = new InputFilter[]{
            new InputFilter.LengthFilter(CEP_DIGITOS.length)};

    private final Validador validador = ValidadorCEP.getInstance();
    private final Validador.ResultadoParcial resultadoParcial = new Validador.ResultadoParcial();

    /**
     * Inicializa o validador com um callback de erros.
     *
     * @param callbackErros Instância que será chamada quando houverem erros.
     */
    public CEPTextWatcher(EventoDeValidacao callbackErros) {
        setEventoDeValidacao(callbackErros);
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (isMudancaInterna()) {
            return;
        }

        s.setFilters(FILTRO_OITO_DIGITOS);

        final StringBuilder builder = trataAdicaoRemocaoDeCaracter(s, CEP_DIGITOS);

        atualizaTexto(validador, resultadoParcial, s, builder);
    }
}
