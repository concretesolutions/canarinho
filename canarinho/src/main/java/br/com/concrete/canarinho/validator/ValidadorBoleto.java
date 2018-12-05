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

    public static final int PRIMEIRO_BLOCO_INICIO_NORMAL = 0;
    public static final int PRIMEIRO_BLOCO_FIM_NORMAL = 10;

    public static final int PRIMEIRO_BLOCO_INICIO_MASCARA = PRIMEIRO_BLOCO_INICIO_NORMAL;
    public static final int PRIMEIRO_BLOCO_FIM_MASCARA = PRIMEIRO_BLOCO_FIM_NORMAL +1;
    public static final int PRIMEIRO_BLOCO_INICIO_MASCARA_TRIBUTO = PRIMEIRO_BLOCO_INICIO_NORMAL;
    public static final int PRIMEIRO_BLOCO_FIM_MASCARA_TRIBUTO = PRIMEIRO_BLOCO_FIM_NORMAL +2;

    public static final int SEGUNDO_BLOCO_INICIO_NORMAL = 10;
    public static final int SEGUNDO_BLOCO_FIM_NORMAL = 21;

    public static final int SEGUNDO_BLOCO_INICIO_MASCARA = SEGUNDO_BLOCO_INICIO_NORMAL +1;
    public static final int SEGUNDO_BLOCO_FIM_MASCARA = SEGUNDO_BLOCO_FIM_NORMAL +3;
    public static final int SEGUNDO_BLOCO_INICIO_MASCARA_TRIBUTO = SEGUNDO_BLOCO_INICIO_NORMAL +2;
    public static final int SEGUNDO_BLOCO_FIM_MASCARA_TRIBUTO = SEGUNDO_BLOCO_FIM_NORMAL +4;

    public static final int TERCEIRO_BLOCO_INICIO_NORMAL = 21;
    public static final int TERCEIRO_BLOCO_FIM_NORMAL = 32;

    public static final int TERCEIRO_BLOCO_INICIO_MASCARA = TERCEIRO_BLOCO_INICIO_NORMAL +3;
    public static final int TERCEIRO_BLOCO_FIM_MASCARA = TERCEIRO_BLOCO_FIM_NORMAL +5;
    public static final int TERCEIRO_BLOCO_INICIO_MASCARA_TRIBUTO = TERCEIRO_BLOCO_INICIO_NORMAL +4;
    public static final int TERCEIRO_BLOCO_FIM_MASCARA_TRIBUTO = TERCEIRO_BLOCO_FIM_NORMAL +6;

    public static final int PRIMEIRO_BLOCO_INICIO_TRIBUTO = 0;
    public static final int PRIMEIRO_BLOCO_FIM_TRIBUTO = 12;

    public static final int SEGUNDO_BLOCO_INICIO_TRIBUTO = 12;
    public static final int SEGUNDO_BLOCO_FIM_TRIBUTO = 24;

    public static final int TERCEIRO_BLOCO_INICIO_TRIBUTO = 24;
    public static final int TERCEIRO_BLOCO_FIM_TRIBUTO = 36;

    public static final int QUARTO_BLOCO_INICIO_TRIBUTO = 36;
    public static final int QUARTO_BLOCO_FIM_TRIBUTO = 48;
    public static final int QUARTO_BLOCO_INICIO_MASCARA = QUARTO_BLOCO_INICIO_TRIBUTO +3;
    public static final int QUARTO_BLOCO_FIM_MASCARA = QUARTO_BLOCO_FIM_TRIBUTO +3;

    public enum TipoBoleto {
        NORMAL, TRIBUTO
    }

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
        boolean ehUmMaisInvalido = false;

        if (!validaBloco(valor, rParcial, MOD_10, PRIMEIRO_BLOCO_FIM_NORMAL, PRIMEIRO_BLOCO_INICIO_NORMAL, "Primeiro", mensagens)) {
            ehUmMaisInvalido = true;
        }

        if (!validaBloco(valor, rParcial, MOD_10, SEGUNDO_BLOCO_FIM_NORMAL, SEGUNDO_BLOCO_INICIO_NORMAL, "Segundo", mensagens)) {
            ehUmMaisInvalido = true;
        }

        if (!validaBloco(valor, rParcial, MOD_10, TERCEIRO_BLOCO_FIM_NORMAL, TERCEIRO_BLOCO_INICIO_NORMAL, "Terceiro", mensagens)) {
            ehUmMaisInvalido = true;
        }

        montaMensagem(mensagens, rParcial);

        return validaValorInvalido(ehUmMaisInvalido, rParcial, valor, TipoBoleto.NORMAL);
    }

    private ResultadoParcial validaTributo(String valor, ResultadoParcial rParcial) {

        if (valor.length() < 3) {
            return rParcial.parcialmenteValido(true);
        }

        // A validação precisa levar em conta o terceiro dígito
        final boolean ehMod10 = valor.charAt(2) == '6' || valor.charAt(2) == '7';
        final DigitoPara digitoPara = ehMod10 ? MOD_10 : MOD_11;

        boolean ehUmMaisInvalido = false;

        List<String> mensagens = new ArrayList<>();
        if (!validaBloco(valor, rParcial, digitoPara, PRIMEIRO_BLOCO_FIM_TRIBUTO, PRIMEIRO_BLOCO_INICIO_TRIBUTO, "Primeiro", mensagens)) {
            ehUmMaisInvalido = true;
        }

        if (!validaBloco(valor, rParcial, digitoPara, SEGUNDO_BLOCO_FIM_TRIBUTO, SEGUNDO_BLOCO_INICIO_TRIBUTO, "Segundo", mensagens)) {
            ehUmMaisInvalido = true;
        }

        if (!validaBloco(valor, rParcial, digitoPara, TERCEIRO_BLOCO_FIM_TRIBUTO, TERCEIRO_BLOCO_INICIO_TRIBUTO, "Terceiro", mensagens)) {
            ehUmMaisInvalido = true;
        }

        if (!validaBloco(valor, rParcial, digitoPara, QUARTO_BLOCO_FIM_TRIBUTO, QUARTO_BLOCO_INICIO_TRIBUTO, "Quarto", mensagens)) {
            ehUmMaisInvalido = true;
        }

        montaMensagem(mensagens, rParcial);

        // Retorna bloco válido
        return validaValorInvalido(ehUmMaisInvalido, rParcial, valor, TipoBoleto.TRIBUTO);
    }

    private void montaMensagem(final List<String> mensagens, final ResultadoParcial rParcial) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mensagens.size(); i++) {
            builder.append(mensagens.get(i));
            if (i != mensagens.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(" bloco(s) inválido(s)");
        rParcial.mensagem(builder.toString());
    }

    private Validador.ResultadoParcial validaValorInvalido(boolean ehUmMaisInvalido,
                                                           Validador.ResultadoParcial rParcial,
                                                           String valor, TipoBoleto tipoBoleto) {
        if (!ehUmMaisInvalido) {
            rParcial.parcialmenteValido(true);
        }

        if (tipoBoleto == TipoBoleto.TRIBUTO) {
            if (valor.length() < 48) return rParcial;
        } else {
            if (valor.length() < 47) return rParcial;
        }

        if (!ehUmMaisInvalido){
            return rParcial.parcialmenteValido(true).totalmenteValido(true);
        }
        return rParcial;
    }

    private boolean ehTributo(CharSequence valor) {
        return valor.charAt(0) == '8';
    }

    private boolean validaBloco(String valor, ResultadoParcial resultadoParcial, DigitoPara mod,
                                int tamanhoMinimo, int st, String mensagem, List<String> mensagens) {

        if (tamanhoMinimo > valor.length()) {
            return true;
        }

        if (valor.length() < tamanhoMinimo) {
            return true;
        }

        final int end = tamanhoMinimo - 1;
        // Valida primeiro bloco
        final char digito = mod.calcula(valor.subSequence(st, end).toString()).charAt(0);

        if (digito != valor.charAt(end)) {
            mensagens.add(mensagem);
            return resultadoParcial
                    .parcialmenteValido(false)
                    .isParcialmenteValido();
        }

        return true;
    }

    private static class SingletonHolder {
        private static final ValidadorBoleto INSTANCE = new ValidadorBoleto();
    }
}
