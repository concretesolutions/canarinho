package br.com.concretesolutions.canarinho.formatador;

import java.util.regex.Pattern;

/** */
public final class FormatadorBoleto implements Formatador {

    // No instance creation
    private FormatadorBoleto() {
    }

    private static final Pattern TRIBUTO_FORMATADO = Pattern.compile("(\\d{12})\\s(\\d{12})\\s(\\d{12})\\s(\\d{12})");
    private static final Pattern TRIBUTO_DESFORMATADO = Pattern.compile("(\\d{12})(\\d{12})(\\d{12})(\\d{12})");
    private static final FormatadorBase FORMATADOR_TRIBUTOS = new FormatadorBase(TRIBUTO_FORMATADO, "$1 $2 $3 $4", TRIBUTO_DESFORMATADO, "$1$2$3$4");

    private static final Pattern NORMAL_FORMATADO = Pattern.compile("(\\d{5})[.](\\d{5})\\s(\\d{5})[.](\\d{6})\\s(\\d{5})[.](\\d{6})\\s(\\d)\\s(\\d{14})");
    private static final Pattern NORMAL_DESFORMATADO = Pattern.compile("(\\d{5})(\\d{5})(\\d{5})(\\d{6})(\\d{5})(\\d{6})(\\d{1})(\\d{14})");
    private static final FormatadorBase FORMATADOR_NORMAL = new FormatadorBase(NORMAL_FORMATADO, "$1.$2 $3.$4 $5.$6 $7 $8", NORMAL_DESFORMATADO, "$1$2$3$4$5$6$7$8");

    @Override
    public String formata(String value) {

        if (ehTributo(value))
            return FORMATADOR_TRIBUTOS.formata(value);

        return FORMATADOR_NORMAL.formata(value);
    }

    @Override
    public String desformata(String value) {

        if (ehTributo(value))
            return FORMATADOR_TRIBUTOS.desformata(value);

        return FORMATADOR_NORMAL.desformata(value);
    }

    @Override
    public boolean estaFormatado(String value) {

        if (ehTributo(value))
            return FORMATADOR_TRIBUTOS.estaFormatado(value);

        return FORMATADOR_NORMAL.estaFormatado(value);
    }

    @Override
    public boolean podeSerFormatado(String value) {

        if (ehTributo(value))
            return FORMATADOR_TRIBUTOS.podeSerFormatado(value);

        return FORMATADOR_NORMAL.podeSerFormatado(value);
    }

    private boolean ehTributo(String value) {

        if (value == null)
            throw new IllegalArgumentException("Valor não pode ser nulo");

        return value.charAt(0) == '8';
    }

    private static class SingletonHolder {
        private static final FormatadorBoleto INSTANCE = new FormatadorBoleto();
    }

    static FormatadorBoleto getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
