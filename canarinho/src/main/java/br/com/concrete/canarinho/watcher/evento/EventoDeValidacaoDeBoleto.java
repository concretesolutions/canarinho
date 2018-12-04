package br.com.concrete.canarinho.watcher.evento;

import java.util.List;

/**
 * Evento de validação específico para boletos que permite saber qual o bloco que
 * contém caracteres inválidos.
 */
public interface EventoDeValidacaoDeBoleto extends EventoDeValidacao {

    /**
     * Invocado quando os números digitados estão inválidos. Pode ser apenas um trecho ou o número completo.
     *
     * @param valorAtual    O valor após a digitação.
     * @param blocoInvalido Os blocos com valor inválido
     */
    void invalido(String valorAtual, List<Integer> blocoInvalido);

}
