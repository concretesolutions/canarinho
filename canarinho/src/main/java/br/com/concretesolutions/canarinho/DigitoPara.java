package br.com.concretesolutions.canarinho;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Uma fluent interface para o cálculo de dígitos, que é usado em diversos boletos e
 * documentos.
 * <p>
 * Para exemplificar, o dígito do trecho 0000039104766 para os multiplicadores indo de
 * 2 a 7 e usando módulo 11 é a seguinte:
 * </p>
 * <pre>
 *  0  0  0  0  0  3  9  1  0  4  7  6  6 (trecho numérico)
 *  2  7  6  5  4  3  2  7  6  5  4  3  2 (multiplicadores, da direita para a esquerda e ciclando)
 *  ----------------------------------------- multiplicações algarismo a algarismo
 *   0  0  0  0  0  9 18  7  0 20 28 18 12 -- soma = 112
 * </pre>
 * <p>
 * Tira-se o módulo dessa soma e, então, calcula-se o complementar do módulo e, se o número
 * for 0, 10 ou 11, o dígito passa a ser 1.
 * </p>
 * <pre>
 *      soma = 112
 *      soma % 11 = 2
 *      11 - (soma % 11) = 9
 * </pre>
 * <p>
 * NOTE: Esta é uma versão otimizada para Android inspirada em
 * https://github.com/caelum/caelum-stella/blob/master/stella-core/src/main/java/br/com/caelum/stella/DigitoPara.java
 * </p>
 */
public final class DigitoPara {

    private final List<Integer> numero = new LinkedList<>();
    private final List<Integer> multiplicadores;
    private final boolean complementar;
    private final int modulo;
    private final boolean somarIndividual;
    private final SparseArray<String> substituicoes;

    private DigitoPara(Builder builder) {

        multiplicadores = builder.multiplicadores;
        complementar = builder.complementar;
        modulo = builder.modulo;
        somarIndividual = builder.somarIndividual;
        substituicoes = builder.substituicoes;
    }

    /**
     * Faz a soma geral das multiplicações dos algarismos pelos multiplicadores, tira o
     * módulo e devolve seu complementar.
     *
     * @param trecho Bloco para calcular o dígito
     * @return String o dígito vindo do módulo com o número passado e configurações extra.
     */
    public final String calcula(String trecho) {

        numero.clear();

        final char[] digitos = trecho.toCharArray();

        for (int i = 0; i < digitos.length; i++) {
            numero.add(Character.getNumericValue(digitos[i]));
        }

        Collections.reverse(numero);

        int soma = 0;
        int multiplicadorDaVez = 0;

        for (int i = 0; i < numero.size(); i++) {
            final int multiplicador = multiplicadores.get(multiplicadorDaVez);
            final int total = numero.get(i) * multiplicador;
            soma += somarIndividual ? somaDigitos(total) : total;
            multiplicadorDaVez = proximoMultiplicador(multiplicadorDaVez);
        }

        int resultado = soma % modulo;

        if (complementar) {
            resultado = modulo - resultado;
        }

        if (substituicoes.get(resultado) != null) {
            return substituicoes.get(resultado);
        }

        return String.valueOf(resultado);
    }


    /*
     * soma os dígitos do número (até 2)
     *
     * Ex: 18 => 9 (1+8), 12 => 3 (1+2)
     */
    private int somaDigitos(int total) {
        return (total / 10) + (total % 10);
    }

    /*
     * Devolve o próximo multiplicador a ser usado, isto é, a próxima posição da lista de
     * multiplicadores ou, se chegar ao fim da lista, a primeira posição, novamente.
     */
    private int proximoMultiplicador(int multiplicadorDaVez) {

        int multiplicador = multiplicadorDaVez + 1;

        if (multiplicador == multiplicadores.size()) {
            multiplicador = 0;
        }

        return multiplicador;
    }

    /**
     * Builder com interface fluente para criação de instâncias configuradas de
     * {@link DigitoPara}
     */
    public static final class Builder {

        private List<Integer> multiplicadores = new ArrayList<>();
        private boolean complementar;
        private int modulo;
        private boolean somarIndividual;
        private final SparseArray<String> substituicoes = new SparseArray<String>();

        /**
         * @param modulo Inteiro pelo qual o resto será tirado e também seu complementar.
         *               O valor padrão é 11.
         * @return this
         */
        public final Builder mod(int modulo) {
            this.modulo = modulo;
            return this;
        }

        /**
         * Para multiplicadores (ou pesos) sequenciais e em ordem crescente, esse método permite
         * criar a lista de multiplicadores que será usada ciclicamente, caso o número base seja
         * maior do que a sequência de multiplicadores. Por padrão os multiplicadores são iniciados
         * de 2 a 9. No momento em que você inserir outro valor este default será sobrescrito.
         *
         * @param inicio Primeiro número do intervalo sequencial de multiplicadores
         * @param fim    Último número do intervalo sequencial de multiplicadores
         * @return this
         */
        public final Builder comMultiplicadoresDeAte(int inicio, int fim) {

            this.multiplicadores.clear();

            for (int i = inicio; i <= fim; i++) {
                multiplicadores.add(i);
            }

            return this;
        }

        /**
         * <p>
         * Indica se, ao calcular o módulo, a soma dos resultados da multiplicação deve ser
         * considerado digito a dígito.
         * </p>
         * Ex: 2 X 9 = 18, irá somar 9 (1 + 8) invés de 18 ao total.
         *
         * @return this
         */
        public final Builder somandoIndividualmente() {
            this.somarIndividual = true;
            return this;
        }

        /**
         * É comum que os geradores de dígito precisem do complementar do módulo em vez
         * do módulo em sí. Então, a chamada desse método habilita a flag que é usada
         * no método mod para decidir se o resultado devolvido é o módulo puro ou seu
         * complementar.
         *
         * @return this
         */
        public final Builder complementarAoModulo() {
            this.complementar = true;
            return this;
        }

        /**
         * Troca por uma String caso encontre qualquer dos inteiros passados como argumento
         *
         * @param substituto String para substituir
         * @param i          varargs de inteiros a serem substituídos
         * @return this
         */
        public final Builder trocandoPorSeEncontrar(String substituto, Integer... i) {

            substituicoes.clear();

            for (Integer integer : i) {
                substituicoes.put(integer, substituto);
            }

            return this;
        }

        /**
         * Há documentos em que os multiplicadores não usam todos os números de um intervalo
         * ou alteram sua ordem. Nesses casos, a lista de multiplicadores pode ser passada
         * através de varargs.
         *
         * @param multiplicadoresEmOrdem Sequência de inteiros com os multiplicadores em ordem
         * @return this
         */
        public final Builder comMultiplicadores(Integer... multiplicadoresEmOrdem) {
            this.multiplicadores.clear();
            this.multiplicadores.addAll(Arrays.asList(multiplicadoresEmOrdem));
            return this;
        }

        /**
         * Método responsável por criar o DigitoPara.
         * Este método inicializará os seguintes valores padrões:
         * <ul>
         * <li>multiplicadores:  2 a 9</li>
         * <li>módulo: 11</li>
         * </ul>
         *
         * @return A instância imutável de DigitoPara
         */
        public final DigitoPara build() {

            if (multiplicadores.size() == 0) {
                comMultiplicadoresDeAte(2, 9);
            }

            if (modulo == 0) {
                mod(11);
            }

            return new DigitoPara(this);
        }
    }
}
