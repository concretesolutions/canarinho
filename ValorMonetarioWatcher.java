package br.com.concretesolutions.canarinho.watcher;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.concretesolutions.canarinho.formatador.Formatador;
import br.com.concretesolutions.canarinho.formatador.FormatadorValor;

/**
 * TextWatcher para valores monetários
 */
public class ValorMonetarioWatcher implements TextWatcher {

    private final boolean mantemZerosAoLimpar;
    private final boolean comValorPosto;
    private final Formatador formatador;
    private boolean mudancaInterna;

    /**
     * Constrói uma instância sem símbolo de Real (R$).
     */
    public ValorMonetarioWatcher() {
        this(false, true, true);
    }

    /**
     * Constrói uma instância com opção de símbolo de Real (R$).
     *
     * @param comSimboloReal      Flag para acrescentar ou não o símbolo
     * @param mantemZerosAoLimpar Sempre que não houver números (apagar em lote) manter zeros
     * @param comValorPosto       Flag para acrescentar 3 zeros após a vírgula (para valores de posto de gasolina)
     */
    ValorMonetarioWatcher(boolean comSimboloReal, boolean mantemZerosAoLimpar, boolean comValorPosto) {
        if (comSimboloReal && comValorPosto) this.formatador = Formatador.VALOR_COM_SIMBOLO_POSTO;
        else if (comSimboloReal) this.formatador = Formatador.VALOR_COM_SIMBOLO;
        else this.formatador = Formatador.VALOR;

        this.mantemZerosAoLimpar = mantemZerosAoLimpar;
        this.comValorPosto = comValorPosto;
    }

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

        final String somenteNumeros = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS
                .matcher(s.toString())
                .replaceAll("");

        // afterTextChanged é chamado ao rotacionar o dispositivo,
        // essa condição evita que ao rotacionar a tela com o campo vazio ocorra NumberFormatException

        if (somenteNumeros.length() == 0) {
            if (mantemZerosAoLimpar && comValorPosto) {
                atualizaTexto(s, formatador.formata("0000"));
            } else if (mantemZerosAoLimpar && !comValorPosto){
                atualizaTexto(s, formatador.formata("000"));
            }
            return;
        }

        if (!comValorPosto){
            final BigDecimal resultado = new BigDecimal(somenteNumeros)
                    .divide(new BigDecimal(100))
                    .setScale(2, RoundingMode.HALF_DOWN);

            atualizaTexto(s, formatador.formata(resultado.toPlainString()));
        } else {
            final BigDecimal resultado = new BigDecimal(somenteNumeros)
                    .divide(new BigDecimal(1000))
                    .setScale(3, RoundingMode.UNNECESSARY);
            atualizaTexto(s, formatador.formata(resultado.toPlainString()));
        }
    }

    private void atualizaTexto(Editable editable, String valor) {
        mudancaInterna = true;

        InputFilter[] oldFilters = editable.getFilters();

        editable.setFilters(new InputFilter[] {});
        editable.replace(0, editable.length(), valor);

        editable.setFilters(oldFilters);

        if (valor.equals(editable.toString())) {
            // TODO: estudar implantar a manutenção da posição do cursor
            Selection.setSelection(editable, valor.length());
        }

        mudancaInterna = false;
    }

    /**
     * Builder para facilitar a construção de instâncias de {@link ValorMonetarioWatcher}
     */
    public static class Builder {

        private boolean mantemZerosAoLimpar;
        private boolean simboloReal;
        private boolean valorPosto;

        /**
         * Manterá os zeros ao limpar o campo.
         *
         * @return this Fluent interface
         */
        public Builder comMantemZerosAoLimpar() {
            this.mantemZerosAoLimpar = true;
            return this;
        }

        /**
         * Inclui o símbolo de real na formatação.
         *
         * @return this Fluent interface
         */
        public Builder comSimboloReal() {
            this.simboloReal = true;
            return this;
        }

        /**
         * Inclui 3 zeros apos a virgula.
         *
         * @return this Fluent interface
         */
        public Builder comValorPosto() {
            this.valorPosto = true;
            return this;
        }

        /**
         * Constrói a instância
         *
         * @return Watcher para ser usado
         */
        public ValorMonetarioWatcher build() {
            return new ValorMonetarioWatcher(simboloReal, mantemZerosAoLimpar, valorPosto);
        }
    }
}
