package br.com.concretesolutions.canarinho.watcher;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.concretesolutions.canarinho.formatador.Formatador;

/**
 * TextWatcher para valores monetários
 */
public final class ValorMonetarioWatcher implements TextWatcher {

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

        if (mudancaInterna) {
            return;
        }

        final String somenteNumeros = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(s.toString()).replaceAll("");

        // afterTextChanged é chamado ao rotacionar o dispositivo,
        // essa condição evita que ao rotacionar a tela com o campo vazio ocorra NumberFormatException
        if (somenteNumeros.length() == 0) {
            return;
        }

        final BigDecimal resultado = new BigDecimal(somenteNumeros).divide(new BigDecimal(100))
                .setScale(2, RoundingMode.HALF_DOWN);

        atualizaTexto(s, Formatador.VALOR.formata(resultado.toPlainString()));
    }

    private void atualizaTexto(Editable s, String valor) {
        mudancaInterna = true;

        s.replace(0, s.length(), valor);

        if (valor.equals(s.toString())) {
            // TODO: estudar implantar a manutenção da posição do cursor
            Selection.setSelection(s, valor.length());
        }

        mudancaInterna = false;
    }
}
