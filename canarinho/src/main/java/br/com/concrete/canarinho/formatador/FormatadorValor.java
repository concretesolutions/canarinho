package br.com.concrete.canarinho.formatador;

import android.os.Build;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Formatador de valores monetários. Possui duas versões:
 * <ul>
 * <li>Com símbolo do Real {@link Formatador#VALOR_COM_SIMBOLO}</li>
 * <li>Sem símbolo do Real {@link Formatador#VALOR}</li>
 * </ul>
 */
public final class FormatadorValor implements Formatador {

    private static final DecimalFormat FORMATADOR_MOEDA = (DecimalFormat)
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private static final Pattern PADRAO_DECIMAL = Pattern
            .compile("^\\d+(\\.\\d{1,2})?$");
    private static final Pattern PADRAO_MOEDA = Pattern
            .compile("\\d{1,3}(\\.\\d{3})*(,\\d{2})?");

    private static final String SIMBOLO_REAL = "R$ ";

    static {
        final DecimalFormatSymbols decimalSymbols = FORMATADOR_MOEDA.getDecimalFormatSymbols();
        decimalSymbols.setCurrencySymbol("");
        FORMATADOR_MOEDA.setMinimumFractionDigits(2);
        FORMATADOR_MOEDA.setDecimalFormatSymbols(decimalSymbols);
        FORMATADOR_MOEDA.setNegativePrefix("-");
        FORMATADOR_MOEDA.setNegativeSuffix("");
        FORMATADOR_MOEDA.setPositivePrefix("");
        FORMATADOR_MOEDA.setParseBigDecimal(true);
    }

    private final boolean adicionaSimboloReal;

    // No instance creation
    private FormatadorValor(boolean comSimboloReal) {
        adicionaSimboloReal = comSimboloReal;
    }

    /**
     * Busca uma instância do formatador com símbolo ou sem.
     *
     * @param comSimboloReal Flag para saber qual instância buscar.
     * @return FormatadorValor de acordo com a flag
     */
    static FormatadorValor getInstance(boolean comSimboloReal) {
        return comSimboloReal
                ? SingletonHolder.INSTANCE_COM_SIMBOLO
                : SingletonHolder.INSTANCE_SEM_SIMBOLO;
    }

    @Override
    public String formata(String value) {
        final String resultado = FORMATADOR_MOEDA.format(new BigDecimal(value));
        return adicionaSimboloReal ? SIMBOLO_REAL + resultado : resultado;
    }

    @Override
    public String desformata(String value) {

        String realValue = value;
        if (value.startsWith(SIMBOLO_REAL)) {
            realValue = value.substring(value.indexOf(SIMBOLO_REAL));
        }

        final BigDecimal valor;
        if (Build.VERSION.SDK_INT < 28) {
            valor = (BigDecimal) FORMATADOR_MOEDA.parse(realValue,
                    new ParsePosition(0));
        } else {
            // Implementando o parse manual devido a um bug na API 28
            valor = new BigDecimal(realValue.replaceAll("\\.", "")
                    .replace(",", "."));
        }
        return valor.toPlainString();
    }

    @Override
    public boolean estaFormatado(String value) {

        if (value == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }

        return PADRAO_MOEDA.matcher(value).matches();
    }

    @Override
    public boolean podeSerFormatado(String value) {

        if (value == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }

        return PADRAO_DECIMAL.matcher(value).matches();
    }

    private static class SingletonHolder {
        private static final FormatadorValor INSTANCE_SEM_SIMBOLO = new FormatadorValor(false);
        private static final FormatadorValor INSTANCE_COM_SIMBOLO = new FormatadorValor(true);
    }
}
