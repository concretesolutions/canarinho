package br.com.concretesolutions.canarinho.formatador;

import br.com.concretesolutions.canarinho.DigitoPara;
import br.com.concretesolutions.canarinho.validator.ValidadorBoleto;

/**
 * Transforma a linha digitável de um boleto em um código de boleto e vice-versa. Use o metodo {@link #formata(String)}
 * para transformar a linha digitavel em boleto e {@link #desformata(String)}.
 * <p>
 * Para verificar se um valor esta em linha digitável ou em boleto, usar os métodos:
 * <ul>
 * <li>{@link #estaFormatado(String)}: indicará se está em formata de boleto</li>
 * <li>{@link #podeSerFormatado(String)}: indicará se é uma linha digitável</li>
 * </ul>
 * </p>
 */
public final class FormatadorLinhaDigitavel implements Formatador {

    private FormatadorLinhaDigitavel() {
    }

    static FormatadorLinhaDigitavel getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String formata(String value) {

        if (value == null || value.length() != 44)
            throw new IllegalArgumentException("Linha digitável deve conter 44 caracteres. " + value);

        if (value.startsWith("8")) {

            final String primeiroBloco = value.substring(0, 11);
            final String segundoBloco = value.substring(11, 22);
            final String terceiroBloco = value.substring(22, 33);
            final String quartoBloco = value.substring(33, 44);

            // o terceiro dígito é o de valor real que define se será mod 10 ou mod 11
            final boolean ehMod10 = value.charAt(2) == '6' || value.charAt(2) == '7';
            final DigitoPara mod = ehMod10 ? ValidadorBoleto.MOD_10 : ValidadorBoleto.MOD_11;

            final String primeiroDigito = mod.calcula(primeiroBloco);
            final String segundoDigito = mod.calcula(segundoBloco);
            final String terceiroDigito = mod.calcula(terceiroBloco);
            final String quartoDigito = mod.calcula(quartoBloco);

            return primeiroBloco + primeiroDigito + segundoBloco + segundoDigito
                    + terceiroBloco + terceiroDigito + quartoBloco + quartoDigito;
        }

        String primeiroBloco = value.substring(0, 4); // 4
        String segundoBloco = value.substring(4, 19); // 15
        String terceiroBloco = value.substring(19, 24); // 5
        String quartoBloco = value.substring(24, 34); // 10
        String quintoBloco = value.substring(34, 44); // 10

        // 1 - 3 - 4 - 5 - 2
        final StringBuilder codigoOrdenado = new StringBuilder(primeiroBloco)
                .append(terceiroBloco)
                .append(quartoBloco)
                .append(quintoBloco)
                .append(segundoBloco);

        primeiroBloco = codigoOrdenado.substring(0, 9);
        segundoBloco = codigoOrdenado.substring(9, 19);
        terceiroBloco = codigoOrdenado.substring(19, 29);
        quartoBloco = codigoOrdenado.substring(29);

        final String primeiroDigito = ValidadorBoleto.MOD_10.calcula(primeiroBloco);
        final String segundoDigito = ValidadorBoleto.MOD_10.calcula(segundoBloco);
        final String terceiroDigito = ValidadorBoleto.MOD_10.calcula(terceiroBloco);

        return primeiroBloco + primeiroDigito + segundoBloco + segundoDigito
                + terceiroBloco + terceiroDigito + quartoBloco;
    }

    @Override
    public String desformata(String valor) {

        if (valor == null || "".equals(valor))
            throw new IllegalArgumentException("Valor não pode estar nulo.");

        String valorDesformatadao = Padroes.PADRAO_SOMENTE_NUMEROS.matcher(valor).replaceAll("");

        if (valorDesformatadao.charAt(0) == '8') {

            if (valorDesformatadao.length() != 48)
                throw new IllegalArgumentException("Valor para boletos que iniciam com 8 deve conter 48 dígitos");

            final StringBuilder builder = new StringBuilder(valorDesformatadao);

            final String primeiroBloco = builder.substring(0, 11);
            final String segundoBloco = builder.substring(12, 23);
            final String terceiroBloco = builder.substring(24, 35);
            final String quartoBloco = builder.substring(36, 47);

            return "" + primeiroBloco + segundoBloco + terceiroBloco + quartoBloco;
        }

        if (valorDesformatadao.length() != 47)
            throw new IllegalArgumentException("Valor para boletos deve conter 47 digitos");

        String primeiroBloco = valorDesformatadao.substring(0, 9);
        String segundoBloco = valorDesformatadao.substring(10, 20);
        String terceiroBloco = valorDesformatadao.substring(21, 31);
        String quartoBloco = valorDesformatadao.substring(32, valorDesformatadao.length());

        final StringBuilder boletoOrdenado = new StringBuilder(primeiroBloco)
                .append(segundoBloco)
                .append(terceiroBloco)
                .append(quartoBloco);

        // 1 - 3 - 4 - 5 - 2
        primeiroBloco = boletoOrdenado.substring(0, 4); // 4
        segundoBloco = boletoOrdenado.substring(29, 44); // 15
        terceiroBloco = boletoOrdenado.substring(4, 9); // 5
        quartoBloco = boletoOrdenado.substring(9, 19); // 10
        final String quintoBloco = boletoOrdenado.substring(19, 29); // 10

        return "" + primeiroBloco + segundoBloco + terceiroBloco + quartoBloco + quintoBloco;
    }

    @Override
    public boolean estaFormatado(String value) {
        return Formatador.BOLETO.estaFormatado(value);
    }

    @Override
    public boolean podeSerFormatado(String value) {
        final String sanitizado = Padroes.PADRAO_SOMENTE_NUMEROS.matcher(value).replaceAll("");
        return sanitizado.length() == 44;
    }

    private static class SingletonHolder {
        private static final FormatadorLinhaDigitavel INSTANCE = new FormatadorLinhaDigitavel();
    }
}
