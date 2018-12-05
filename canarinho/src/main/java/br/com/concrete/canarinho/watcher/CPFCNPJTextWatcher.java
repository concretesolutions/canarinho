package br.com.concrete.canarinho.watcher;

import android.text.Editable;
import android.text.InputFilter;

import br.com.concrete.canarinho.formatador.Formatador;
import br.com.concrete.canarinho.validator.Validador;
import br.com.concrete.canarinho.validator.ValidadorCPFCNPJ;
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao;

/**
 * {@link android.text.TextWatcher} responsável por formatar e validar um
 * {@link android.widget.EditText} para CPF / CNPJ.
 * Para usar este componente basta criar uma instância e chamar
 * {@link android.widget.EditText#addTextChangedListener(android.text.TextWatcher)}.
 */
public class CPFCNPJTextWatcher extends BaseCanarinhoTextWatcher {

    private static final char[] CPF = "###.###.###-##".toCharArray();
    private static final char[] CNPJ = "##.###.###/####-##".toCharArray();
    private static final InputFilter[] FILTRO_CPF_CNPJ = new InputFilter[]{new InputFilter.LengthFilter(CNPJ.length)};

    private final Validador validador = ValidadorCPFCNPJ.getInstance();
    private final Validador.ResultadoParcial resultadoParcial = new Validador.ResultadoParcial();

    /**
     * TODO Javadoc pendente
     */
    public CPFCNPJTextWatcher() {
    }

    /**
     * TODO Javadoc pendente
     *
     * @param callbackErros a descrever
     */
    public CPFCNPJTextWatcher(EventoDeValidacao callbackErros) {
        setEventoDeValidacao(callbackErros);
    }

    @Override
    public void afterTextChanged(final Editable s) {

        if (isMudancaInterna()) {
            return;
        }

        s.setFilters(FILTRO_CPF_CNPJ);

        final char[] mascara = ehCpf(s) ? CPF : CNPJ;
        final StringBuilder builder = trataAdicaoRemocaoDeCaracter(s, mascara);
        atualizaTexto(validador, resultadoParcial, s, builder, false);
    }

    // Verifica se o valor informado é cpf
    private boolean ehCpf(Editable e) {
        return Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(e).replaceAll("").length() < 12;
    }
}