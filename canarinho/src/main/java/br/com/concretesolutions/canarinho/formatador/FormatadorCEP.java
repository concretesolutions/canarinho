package br.com.concretesolutions.canarinho.formatador;

/**
 * Formata CEP
 * Ver exemplos no Sample.
 */
public final class FormatadorCEP implements Formatador {

    private FormatadorCEP() {
    }

    static FormatadorCEP getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String formata(final String value) {
        return Formatador.CEP.formata(value);
    }

    @Override
    public String desformata(final String value) {
        return Formatador.CEP.desformata(value);
    }

    @Override
    public boolean estaFormatado(final String value) {
        return Formatador.CEP.estaFormatado(value);
    }

    @Override
    public boolean podeSerFormatado(final String value) {
        if (value == null) {
            return false;
        }

        return Formatador.CEP.podeSerFormatado(value);
    }

    private static class SingletonHolder {
        private static final FormatadorCEP INSTANCE = new FormatadorCEP();
    }
}