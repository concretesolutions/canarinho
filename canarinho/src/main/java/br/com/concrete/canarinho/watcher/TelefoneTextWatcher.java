package br.com.concrete.canarinho.watcher;

import android.text.Editable;
import android.text.InputFilter;

import br.com.concrete.canarinho.formatador.Formatador;
import br.com.concrete.canarinho.validator.Validador;
import br.com.concrete.canarinho.validator.ValidadorTelefone;
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao;

/**
 * {@link android.text.TextWatcher} responsável por formatar e validar um
 * {@link android.widget.EditText} para telefones.
 * Para usar este componente basta criar uma instância e chamar
 * {@link android.widget.EditText#addTextChangedListener(android.text.TextWatcher)}.
 */
public final class TelefoneTextWatcher extends BaseCanarinhoTextWatcher {

    private static final char[] TELEFONE_OITO_DIGITOS = "(##) ####-####".toCharArray();
    private static final char[] TELEFONE_NOVE_DIGITOS = "(##) #####-####".toCharArray();
    private static final InputFilter[] FILTRO_NOVE_DIGITOS = new InputFilter[]{
            new InputFilter.LengthFilter(TELEFONE_NOVE_DIGITOS.length)};

    private final Validador validador = ValidadorTelefone.getInstance();
    private final Validador.ResultadoParcial resultadoParcial = new Validador.ResultadoParcial();

    /**
     * TODO Javadoc pendente
     *
     * @param callbackErros a descrever
     */
    public TelefoneTextWatcher(EventoDeValidacao callbackErros) {
        setEventoDeValidacao(callbackErros);
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (isMudancaInterna()) {
            return;
        }

        s.setFilters(FILTRO_NOVE_DIGITOS);

        final char[] mascara = ehNoveDigitos(s) ? TELEFONE_NOVE_DIGITOS : TELEFONE_OITO_DIGITOS;
        final StringBuilder builder = trataAdicaoRemocaoDeCaracter(s, mascara);

        atualizaTexto(validador, resultadoParcial, s, builder, false);
    }

    // Verifica se o telefone possui 9 dígitos
    private boolean ehNoveDigitos(Editable e) {
        return Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(e).replaceAll("").length() > 10;
    }
}
