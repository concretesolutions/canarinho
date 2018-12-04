package br.com.concrete.canarinho.validator;

import android.text.Editable;
import android.text.SpannableStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.concrete.canarinho.DigitoPara;
import br.com.concrete.canarinho.formatador.Formatador;

/**
 * Implementação de @{link Validador} para boleto
 *
 * @see Validador
 */
public final class ValidadorBoleto implements Validador {

    /**
     * Instância de módulo 10 para cálculo de digito verificador de boleto
     */
    public static final DigitoPara MOD_10 = new DigitoPara.Builder()
            .mod(10)
            .comMultiplicadores(2, 1)
            .somandoIndividualmente()
            .trocandoPorSeEncontrar("0", 10)
            .complementarAoModulo()
            .build();

    /**
     * Instância de módulo 11 para cálculo de digito verificador de boleto
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

    private ResultadoParcial validaNormal(String valor, ResultadoParcial rParcial) {
        List<String> mensagens = new ArrayList<>();
        if (!validaBloco(valor, rParcial, MOD_10, 10, 0, "Primeiro")) {
            mensagens.add("Primeiro");
        }

        if (!validaBloco(valor, rParcial, MOD_10, 21, 10, "Segundo")) {
            mensagens.add("Segundo");
        }

        if (!validaBloco(valor, rParcial, MOD_10, 32, 21, "Terceiro")) {
            mensagens.add("Terceiro");
        }

        if (mensagens.size() == 1) {
            return rParcial;
        } else if (mensagens.size() > 1) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mensagens.size(); i++) {
                builder.append(mensagens.get(i));
                if (i != mensagens.size() - 1) {
                    builder.append(", ");
                }
            }
            builder.append(" blocos inválidos");
            return rParcial
                    .mensagem(builder.toString());
        }

        if (valor.length() < 47) {
            return rParcial;
        }

        return rParcial.parcialmenteValido(true).totalmenteValido(true);
    }

    private ResultadoParcial validaTributo(String valor, ResultadoParcial rParcial) {

        if (valor.length() < 3) {
            return rParcial.parcialmenteValido(true);
        }

        // A validação precisa levar em conta o terceiro dígito
        final boolean ehMod10 = valor.charAt(2) == '6' || valor.charAt(2) == '7';
        final DigitoPara digitoPara = ehMod10 ? MOD_10 : MOD_11;

        List<String> mensagens = new ArrayList<>();
        if (!validaBloco(valor, rParcial, digitoPara, 12, 0, "Primeiro")) {
            mensagens.add("Primeiro");
        }

        if (!validaBloco(valor, rParcial, digitoPara, 24, 12, "Segundo")) {
            mensagens.add("Segundo");
        }

        if (!validaBloco(valor, rParcial, digitoPara, 36, 24, "Terceiro")) {
            mensagens.add("Terceiro");
        }

        if (!validaBloco(valor, rParcial, digitoPara, 48, 36, "Quarto")) {
            mensagens.add("Quarto");
        }

        if (mensagens.size() == 1) {
            return rParcial;
        } else if (mensagens.size() > 1) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mensagens.size(); i++) {
                builder.append(mensagens.get(i));
                if (i != mensagens.size() - 1) {
                    builder.append(", ");
                }
            }
            builder.append(" blocos inválidos");
            return rParcial
                    .mensagem(builder.toString());
        }

        // Retorna bloco válido
        return rParcial.parcialmenteValido(true).totalmenteValido(true);
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
