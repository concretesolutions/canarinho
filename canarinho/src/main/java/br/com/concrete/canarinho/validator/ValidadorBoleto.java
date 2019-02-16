package br.com.concrete.canarinho.validator;

import android.text.Editable;
import android.text.SpannableStringBuilder;

import br.com.concrete.canarinho.DigitoPara;
import br.com.concrete.canarinho.formatador.Formatador;

import java.util.regex.Pattern;

/**
 * Implementação de @{link Validador} para boleto.
 *
 * @see Validador
 */
public final class ValidadorBoleto implements Validador {

    /**
     * Instância de módulo 10 para cálculo de digito verificador de boleto.
     */
    public static final DigitoPara MOD_10 = new DigitoPara.Builder()
            .mod(10)
            .comMultiplicadores(2, 1)
            .somandoIndividualmente()
            .trocandoPorSeEncontrar("0", 10)
            .complementarAoModulo()
            .build();

    /**
     * Instância de módulo 11 para cálculo de digito verificador de boleto.
     */
    public static final DigitoPara MOD_11 = new DigitoPara.Builder()
            .trocandoPorSeEncontrar("0", 10, 11)
            .complementarAoModulo()
            .build();

    private static final Pattern PADRAO_PARA_LIMPAR = Pattern.compile("[\\s.]");
    private static final Pattern PADRAO_APENAS_NUMEROS = Pattern.compile("[\\d]*");

    // No instance creation
    private ValidadorBoleto() {
    }

    public static ValidadorBoleto getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean ehValido(String valor) {

        if (valor == null) {
            throw new IllegalArgumentException("Campos não podem ser nulos");
        }

        String valorSemFormatacao = Formatador.Padroes.PADRAO_SOMENTE_NUMEROS.matcher(valor).replaceAll("");
        return ehValido(new SpannableStringBuilder(valorSemFormatacao), new ResultadoParcial()).isValido();
    }

    @Override
    public ResultadoParcial ehValido(Editable valor, ResultadoParcial resultadoParcial) {

        if (resultadoParcial == null || valor == null) {
            throw new IllegalArgumentException("Campos não podem ser nulos");
        }

        final String valorDesformatado = PADRAO_PARA_LIMPAR.matcher(valor).replaceAll("");

        if (!PADRAO_APENAS_NUMEROS.matcher(valorDesformatado).matches()) {
            throw new IllegalArgumentException("Apenas números, '.' e espaços são válidos");
        }

        resultadoParcial.totalmenteValido(false);

        if (valorDesformatado.length() == 0) {
            return resultadoParcial
                    .parcialmenteValido(true)
                    .mensagem("");
        }

        return ehTributo(valorDesformatado)
                ? validaTributo(valorDesformatado, resultadoParcial)
                : validaNormal(valorDesformatado, resultadoParcial);
    }

    private ResultadoParcial validaNormal(String valor, ResultadoParcial resultadoParcial) {

        if (!validaBloco(valor, resultadoParcial, MOD_10, 10, 0, "Primeiro")) {
            return resultadoParcial;
        }

        if (!validaBloco(valor, resultadoParcial, MOD_10, 21, 10, "Segundo")) {
            return resultadoParcial;
        }

        if (!validaBloco(valor, resultadoParcial, MOD_10, 32, 21, "Terceiro")) {
            return resultadoParcial;
        }

        if (valor.length() < 47) {
            return resultadoParcial;
        }

        return resultadoParcial.parcialmenteValido(true).totalmenteValido(true);
    }

    private ResultadoParcial validaTributo(String valor, ResultadoParcial resultadoParcial) {

        if (valor.length() < 3) {
            return resultadoParcial.parcialmenteValido(true);
        }

        // A validação precisa levar em conta o terceiro dígito
        final boolean ehMod10 = valor.charAt(2) == '6' || valor.charAt(2) == '7';
        final DigitoPara digitoPara = ehMod10 ? MOD_10 : MOD_11;

        if (!validaBloco(valor, resultadoParcial, digitoPara, 12, 0, "Primeiro")) {
            return resultadoParcial;
        }

        if (!validaBloco(valor, resultadoParcial, digitoPara, 24, 12, "Segundo")) {
            return resultadoParcial;
        }

        if (!validaBloco(valor, resultadoParcial, digitoPara, 36, 24, "Terceiro")) {
            return resultadoParcial;
        }

        if (!validaBloco(valor, resultadoParcial, digitoPara, 48, 36, "Quarto")) {
            return resultadoParcial;
        }

        // Retorna bloco válido
        return resultadoParcial.parcialmenteValido(true).totalmenteValido(true);
    }

    private boolean ehTributo(CharSequence valor) {
        return valor.charAt(0) == '8';
    }

    private boolean validaBloco(String valor, ResultadoParcial resultadoParcial, DigitoPara mod,
                                int tamanhoMinimo, int st, String mensagem) {

        if (valor.length() < tamanhoMinimo) {
            resultadoParcial.parcialmenteValido(true);
            return false;
        }

        final int end = tamanhoMinimo - 1;
        // Valida primeiro bloco
        final char digito = mod.calcula(valor.subSequence(st, end).toString()).charAt(0);

        if (digito != valor.charAt(end)) {
            return resultadoParcial
                    .mensagem(mensagem + " bloco inválido")
                    .parcialmenteValido(false)
                    .isParcialmenteValido();
        }

        return true;
    }

    private static class SingletonHolder {
        private static final ValidadorBoleto INSTANCE = new ValidadorBoleto();
    }
}
