package br.com.concretesolutions.canarinho.watcher.evento;

/**
 * Interface para quem estiver usando este TextWatcher poder ter uma ação quando um erro de validação acontecer.
 * Os métodos desta interface serão chamados quando for digitado um NOVO caracter e quando for APAGADO um caracter.
 */
public interface EventoDeValidacao {

    /**
     * Invocado quando os números digitados estão inválidos. Pode ser apenas um trecho ou o número completo.
     *
     * @param valorAtual O valor após a digitação.
     * @param mensagem   A mensagem de erro da validação.
     */
    void invalido(String valorAtual, String mensagem);

    /**
     * Invocado quando os números digitados estão parcialmente válidos. Quando o número estiver completamente válido
     * será chamado o callback {@link #totalmenteValido(String)}.
     *
     * @param valorAtual O valor após a digitação.
     */
    void parcialmenteValido(String valorAtual);

    /**
     * Invocado quando a máscara está completa e os números são válidos.
     *
     * @param valorAtual O valor após a digitação.
     */
    void totalmenteValido(String valorAtual);
}
