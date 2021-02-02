package br.com.concrete.canarinho.sample.ui.model;

import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import br.com.concrete.canarinho.sample.ui.fragment.BaseWatcherFragment;
import br.com.concrete.canarinho.sample.ui.fragment.CanarinhoValorMonetarioWatcherFragment;
import br.com.concrete.canarinho.sample.ui.fragment.WatcherFragment;
import br.com.concrete.canarinho.validator.Validador;
import br.com.concrete.canarinho.watcher.BoletoBancarioTextWatcher;
import br.com.concrete.canarinho.watcher.CEPTextWatcher;
import br.com.concrete.canarinho.watcher.CPFCNPJTextWatcher;
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher;
import br.com.concrete.canarinho.watcher.TelefoneTextWatcher;
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao;
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacaoDeBoleto;

/**
 */
public enum Watchers {

    BOLETO_BANCARIO("Boleto Bancário", "Digite um boleto válido") {
        @Override
        public WatcherFragment buildFragment() {
            return WatcherFragment.newInstance(this);
        }

        @Override
        public TextWatcher setupWatcher(TextInputLayout textInputLayout) {
            return new BoletoBancarioTextWatcher(new SampleEventoDeValidacao(textInputLayout));
        }
    },
    BOLETO_BANCARIO_MSG_CUSTOM("Boleto Bancário com mensagem", "Digite um boleto válido") {
        @Override
        public WatcherFragment buildFragment() {
            return WatcherFragment.newInstance(this);
        }

        @Override
        public TextWatcher setupWatcher(TextInputLayout textInputLayout) {
            return new BoletoBancarioTextWatcher(new EventoDeValidacaoBoleto(textInputLayout));
        }
    },

    CPF("CPF", "Digite um CPF válido") {
        @Override
        public WatcherFragment buildFragment() {
            return WatcherFragment.newInstance(this);
        }

        @Override
        public TextWatcher setupWatcher(TextInputLayout textInputLayout) {
            return new MascaraNumericaTextWatcher.Builder()
                    .paraMascara("###.###.###-##")
                    .comCallbackDeValidacao(new SampleEventoDeValidacao(textInputLayout))
                    .comValidador(Validador.CPF)
                    .build();
        }
    },

    CNPJ("CNPJ", "Digite um CNPJ válido") {
        @Override
        public WatcherFragment buildFragment() {
            return WatcherFragment.newInstance(this);
        }

        @Override
        public TextWatcher setupWatcher(TextInputLayout textInputLayout) {
            return new MascaraNumericaTextWatcher.Builder()
                    .paraMascara("##.###.###/####-##")
                    .comCallbackDeValidacao(new SampleEventoDeValidacao(textInputLayout))
                    .comValidador(Validador.CNPJ)
                    .build();
        }
    },

    TELEFONE("Telefone", "Digite um telefone válido") {
        @Override
        public WatcherFragment buildFragment() {
            return WatcherFragment.newInstance(this);
        }

        @Override
        public TextWatcher setupWatcher(TextInputLayout textInputLayout) {
            return new TelefoneTextWatcher(new SampleEventoDeValidacao(textInputLayout));
        }
    },

    CPF_CNPJ("CPF e CNPJ", "Digite um CPF ou CNPJ válido") {
        @Override
        public WatcherFragment buildFragment() {
            return WatcherFragment.newInstance(this);
        }

        @Override
        public TextWatcher setupWatcher(TextInputLayout textInputLayout) {
            return new CPFCNPJTextWatcher(new SampleEventoDeValidacao(textInputLayout));
        }
    },

    CEP("CEP", "Digite um CEP válido") {
        @Override
        public WatcherFragment buildFragment() {
            return WatcherFragment.newInstance(this);
        }

        @Override
        public TextWatcher setupWatcher(TextInputLayout textInputLayout) {
            return new CEPTextWatcher(new SampleEventoDeValidacao(textInputLayout));
        }
    },

    MASCARA_GENERICA("Máscara genérica", "5 números") {
        @Override
        public WatcherFragment buildFragment() {
            return WatcherFragment.newInstance(this);
        }

        @Override
        public TextWatcher setupWatcher(TextInputLayout textInputLayout) {
            return new MascaraNumericaTextWatcher("#-#-#-#-#");
        }
    },

    VALOR_MONETARIO("Valor monetário", "Digite um valor monetário") {
        @Override
        public CanarinhoValorMonetarioWatcherFragment buildFragment() {
            return new CanarinhoValorMonetarioWatcherFragment();
        }

        @Override
        public TextWatcher setupWatcher(TextInputLayout textInputLayout) {
            return null;
        }
    };

    private final String title;
    private final String hint;

    Watchers(String title, String hint) {
        this.hint = hint;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getHint() {
        return hint;
    }

    public abstract BaseWatcherFragment buildFragment();

    public abstract TextWatcher setupWatcher(TextInputLayout textInputLayout);

    /**
     * {@link EventoDeValidacao} que mostra uma mensagem de erro no {@link TextInputLayout} passado
     * como argumento.
     */
    public static class SampleEventoDeValidacao implements EventoDeValidacao {

        TextInputLayout textInputLayout;

        public SampleEventoDeValidacao(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }

        @Override
        public void invalido(String valorAtual, String mensagem) {
            textInputLayout.setError(mensagem);
        }

        @Override
        public void parcialmenteValido(String valorAtual) {
            textInputLayout.setErrorEnabled(false);
            textInputLayout.setError(null);
        }

        @Override
        public void totalmenteValido(String valorAtual) {
            new AlertDialog.Builder(textInputLayout.getContext())
                    .setTitle("Campo válido!")
                    .setMessage(valorAtual)
                    .show();
        }
    }

    /**
     * {@link EventoDeValidacao} que valida blocos de boleto.
     *
     * @see SampleEventoDeValidacao
     */
    public static class EventoDeValidacaoBoleto
            extends SampleEventoDeValidacao
            implements EventoDeValidacaoDeBoleto {

        EventoDeValidacaoBoleto(TextInputLayout textInputLayout) {
            super(textInputLayout);
        }

        @Override
        public void invalido(String valorAtual, int blocoInvalido) {

            if (blocoInvalido == 1)
                textInputLayout.setError("Primeira mensagem");

            else if (blocoInvalido == 2)
                textInputLayout.setError("Segunda mensagem");

            else if (blocoInvalido == 3)
                textInputLayout.setError("Terceira mensagem");

            else if (blocoInvalido == 4)
                textInputLayout.setError("Quarta mensagem");

        }
    }

}
