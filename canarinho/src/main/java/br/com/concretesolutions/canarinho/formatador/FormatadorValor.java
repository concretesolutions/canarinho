package br.com.concretesolutions.canarinho.formatador;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.regex.Pattern;

/** */
public final class FormatadorValor implements Formatador {

    private static final DecimalFormat FORMATADOR_MOEDA = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private static final Pattern PADRAO_DECIMAL = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    private static final Pattern PADRAO_MOEDA = Pattern.compile("\\d{1,3}(\\.\\d{3})*(,\\d{2})?");

    static {
        final DecimalFormatSymbols decimalFormatSymbols = FORMATADOR_MOEDA.getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        FORMATADOR_MOEDA.setMinimumFractionDigits(2);
        FORMATADOR_MOEDA.setDecimalFormatSymbols(decimalFormatSymbols);
        FORMATADOR_MOEDA.setNegativePrefix("-");
        FORMATADOR_MOEDA.setNegativeSuffix("");
        FORMATADOR_MOEDA.setPositivePrefix("");
        FORMATADOR_MOEDA.setParseBigDecimal(true);
    }

    private static class SingletonHolder {
        private static final FormatadorValor INSTANCE = new FormatadorValor();
    }

    static FormatadorValor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // No instance creation
    private FormatadorValor() {
    }

    @Override
    public String formata(String value) {
        return FORMATADOR_MOEDA.format(new BigDecimal(value));
    }

    @Override
    public String desformata(String value) {
        final BigDecimal valor = (BigDecimal) FORMATADOR_MOEDA.parse(value, new ParsePosition(0));
        return valor.toPlainString();
    }

    @Override
    public boolean estaFormatado(String value) {

        if (value == null)
            throw new IllegalArgumentException("Valor não pode ser nulo");

        return PADRAO_MOEDA.matcher(value).matches();
    }

    @Override
    public boolean podeSerFormatado(String value) {

        if (value == null)
            throw new IllegalArgumentException("Valor não pode ser nulo");

        return PADRAO_DECIMAL.matcher(value).matches();
    }
}
