package br.com.concretesolutions.canarinho.validator;

import android.text.Editable;

/**
 * Interface de validação de campos. Há basicamente duas formas de validação:
 * <ul>
 * <li>Uma {@link String} completa</li>
 * <li>Um {@link Editable} e um {@link br.com.concretesolutions.canarinho.validator.Validador.ResultadoParcial}</li>
 * </ul>
 * No primeiro caso o retorno será: true ou false. No segundo caso, o resultado será sempre
 * atualizado no objeto {@link br.com.concretesolutions.canarinho.validator.Validador.ResultadoParcial} passado.
 */
public interface Validador {

    /**
     * Referência para o singleton de validação de CPF
     */
    Validador CPF = ValidadorCPF.getInstance();
    /**
     * Referência para o singleton de validação de CNPJ
     */
    Validador CNPJ = ValidadorCNPJ.getInstance();
    /**
     * Referência para o singleton de validação de boleto
     */
    Validador BOLETO = ValidadorBoleto.getInstance();
    /**
     * Referência para o singleton de validação de telefone
     */
    Validador TELFONE = ValidadorTelefone.getInstance();

    /**
     * Valida uma {@link String} completa
     *
     * @param valor Valor a ser validado
     * @return true se estiver válida e false caso contrário
     */
    boolean ehValido(String valor);

    /**
     * Valida um {@link Editable} retornando o {@link br.com.concretesolutions.canarinho.validator.Validador.ResultadoParcial}
     *
     * @param valor            Editable
     * @param resultadoParcial Objeto com o estado da validação
     * @return Objeto com o estado da validação atualizado
     */
    ResultadoParcial ehValido(Editable valor, ResultadoParcial resultadoParcial);

    /**
     * Value Object com o estado da validação.
     */
    class ResultadoParcial {

        private boolean valido;
        private boolean parcialmenteValido = true;
        private String mensagem;

        public boolean isValido() {
            return valido;
        }

        public boolean isParcialmenteValido() {
            return parcialmenteValido;
        }

        public String getMensagem() {
            return mensagem;
        }

        public ResultadoParcial totalmenteValido(boolean valido) {
            this.valido = valido;
            return this;
        }

        public ResultadoParcial parcialmenteValido(boolean parcialmenteValido) {
            this.parcialmenteValido = parcialmenteValido;
            return this;
        }

        public ResultadoParcial mensagem(String mensagem) {
            this.mensagem = mensagem;
            return this;
        }
    }
}
