package br.com.concretesolutions.canarinho.formatador;

import java.util.regex.Pattern;

/**
 * Interface de formatação. Formata valores completos. Útil caso receba o valor
 * desformatado de uma API.
 */
public interface Formatador {

    // Formatadores
    Formatador CPF = new FormatadorBase(Padroes.CPF_FORMATADO, "$1.$2.$3-$4", Padroes.CPF_DESFORMATADO, "$1$2$3$4");
    Formatador CNPJ = new FormatadorBase(Padroes.CNPJ_FORMATADO, "$1.$2.$3/$4-$5", Padroes.CNPJ_DESFORMATADO, "$1$2$3$4$5");
    Formatador VALOR = FormatadorValor.getInstance();
    Formatador BOLETO = FormatadorBoleto.getInstance();

    interface Padroes {
        // Patterns
        Pattern CNPJ_FORMATADO = Pattern.compile("(\\d{2})[.](\\d{3})[.](\\d{3})/(\\d{4})-(\\d{2})");
        Pattern CNPJ_DESFORMATADO = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
        Pattern CPF_FORMATADO = Pattern.compile("(\\d{3})[.](\\d{3})[.](\\d{3})-(\\d{2})");
        Pattern CPF_DESFORMATADO = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
        Pattern PADRAO_SOMENTE_NUMEROS = Pattern.compile("[^0-9]");
    }

    String formata(String value);

    String desformata(String value);

    boolean estaFormatado(String value);

    boolean podeSerFormatado(String value);

}
