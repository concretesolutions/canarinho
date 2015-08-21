package br.com.concretesolutions.canarinho.formatador;

import java.util.regex.Pattern;

/**
 * Interface de formatação. Formata valores completos. Útil caso receba o valor
 * desformatado de uma API.
 */
public interface Formatador {

    // Formatadores
    /**
     * Singleton de formatação de CPF
     */
    Formatador CPF = new FormatadorBase(Padroes.CPF_FORMATADO, "$1.$2.$3-$4", Padroes.CPF_DESFORMATADO, "$1$2$3$4");

    /**
     * Singleton de formatação de CNPJ
     */
    Formatador CNPJ = new FormatadorBase(Padroes.CNPJ_FORMATADO, "$1.$2.$3/$4-$5", Padroes.CNPJ_DESFORMATADO,
            "$1$2$3$4$5");

    /**
     * Singleton de formatação de CPF e CNPJ
     */
    Formatador CPF_CNPJ = FormatadorCPFCNPJ.getInstance();

    /**
     * Singleton de formatação de valores monetários
     */
    Formatador VALOR = FormatadorValor.getInstance();

    /**
     * Singleton de formatação de boletos bancários
     */
    Formatador BOLETO = FormatadorBoleto.getInstance();

    /**
     * Singleton de formatação de telefones (DDD) número
     */
    Formatador TELEFONE = FormatadorTelefone.getInstance();

    /**
     * Singleton de formatação de linha digitável. Transforma números do código de barras em linha digitável.
     */
    Formatador LINHA_DIGITAVEL = FormatadorLinhaDigitavel.getInstance();

    /**
     * Formata um valor COMPLETO. Deve falhar caso o valor não esteja completo.
     *
     * @param value valor a formatar
     * @return REsultado da formatação
     */
    String formata(String value);

    /**
     * Desformata um valor.
     *
     * @param value Valor a desformatar
     * @return Resultado da desformatação
     */
    String desformata(String value);

    /**
     * Verifica se um parâmetro está formatado.
     *
     * @param value Valor para verificar
     * @return True se estiver formatado, falso caso contrário.
     */
    boolean estaFormatado(String value);

    /**
     * Verifica se um parâmetro pode ser formatado.
     *
     * @param value Valor para verificar
     * @return True se puder ser formatado, falso caso contrário.
     */
    boolean podeSerFormatado(String value);

    /**
     * Classe para guardar os padrões de experssões regulares usados no framework
     */
    abstract class Padroes {
        // Patterns
        public static final Pattern CNPJ_FORMATADO = Pattern
                .compile("(\\d{2})[.](\\d{3})[.](\\d{3})/(\\d{4})-(\\d{2})");
        public static final Pattern CNPJ_DESFORMATADO = Pattern
                .compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
        public static final Pattern CPF_FORMATADO = Pattern
                .compile("(\\d{3})[.](\\d{3})[.](\\d{3})-(\\d{2})");
        public static final Pattern CPF_DESFORMATADO = Pattern
                .compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
        public static final Pattern PADRAO_SOMENTE_NUMEROS = Pattern
                .compile("[^0-9]");
    }

}
