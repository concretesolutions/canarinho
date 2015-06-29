package br.com.concretesolutions.canarinho.watcher;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import br.com.concretesolutions.canarinho.formatador.Formatador;

/**
 */
public final class ValorMonetarioWatcher implements TextWatcher {

    private static final DecimalFormat DUAS_CASAS = new DecimalFormat("##.00");

    private boolean mudancaInterna;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Não faz nada aqui
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Não faz nada aqui
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (mudancaInterna)
            return;

        final String somenteNumeros = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(s.toString()).replaceAll("");
        final BigDecimal resultado = new BigDecimal(somenteNumeros).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_DOWN);

        atualizaTexto(s, Formatador.VALOR.formata(resultado.toPlainString()));
    }

    private void atualizaTexto(Editable s, String valor) {
        mudancaInterna = true;

        final InputFilter[] filters = s.getFilters();
        s.replace(0, s.length(), valor);

        if (valor.equals(s.toString()))
            // TODO: estudar implantar a manutenção da posição do cursor
            Selection.setSelection(s, valor.length());

        mudancaInterna = false;
    }
}
