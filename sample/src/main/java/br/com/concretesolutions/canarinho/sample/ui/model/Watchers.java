package br.com.concretesolutions.canarinho.sample.ui.model;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextWatcher;

import br.com.concretesolutions.canarinho.sample.ui.fragment.BaseWatcherFragment;
import br.com.concretesolutions.canarinho.sample.ui.fragment.CanarinhoValorMonetarioWatcherFragment;
import br.com.concretesolutions.canarinho.sample.ui.fragment.WatcherFragment;
import br.com.concretesolutions.canarinho.validator.Validador;
import br.com.concretesolutions.canarinho.watcher.BoletoBancarioTextWatcher;
import br.com.concretesolutions.canarinho.watcher.CEPTextWatcher;
import br.com.concretesolutions.canarinho.watcher.CPFCNPJTextWatcher;
import br.com.concretesolutions.canarinho.watcher.MascaraNumericaTextWatcher;
import br.com.concretesolutions.canarinho.watcher.TelefoneTextWatcher;
import br.com.concretesolutions.canarinho.watcher.evento.EventoDeValidacao;
import br.com.concretesolutions.canarinho.watcher.evento.EventoDeValidacaoDeBoleto;

/**
 */
public enum Watchers {

    BOLETO_BANCARIO("Boleto Bancário", "Digite um boleto válido") {
        @Override
        public WatcherFragment buildFragment() {
            return new WatcherFragment() {
                @Override
                protected TextWatcher getWatcher(FragmentActivity context) {
                    return new BoletoBancarioTextWatcher(new SampleEventoDeValidacao(context));
                }
            };
        }
    },
    BOLETO_BANCARIO_MSG_CUSTOM("Boleto Bancário com mensagem", "Digite um boleto válido") {
        @Override
        public WatcherFragment buildFragment() {
            return new WatcherFragment() {
                @Override
                protected TextWatcher getWatcher(FragmentActivity context) {
                    return new BoletoBancarioTextWatcher(new EventoDeValidacaoBoleto(context));
                }
            };
        }
    },

    CPF("CPF", "Digite um CPF válido") {
        @Override
        public WatcherFragment buildFragment() {
            return new WatcherFragment() {
                @Override
                protected TextWatcher getWatcher(FragmentActivity activity) {
                    return new MascaraNumericaTextWatcher.Builder()
                            .paraMascara("###.###.###-##")
                            .comCallbackDeValidacao(new SampleEventoDeValidacao(activity))
                            .comValidador(Validador.CPF)
                            .build();
                }
            };
        }
    },

    CNPJ("CNPJ", "Digite um CNPJ válido") {
        @Override
        public WatcherFragment buildFragment() {
            return new WatcherFragment() {
                @Override
                protected TextWatcher getWatcher(FragmentActivity activity) {
                    return new MascaraNumericaTextWatcher.Builder()
                            .paraMascara("##.###.###/####-##")
                            .comCallbackDeValidacao(new SampleEventoDeValidacao(activity))
                            .comValidador(Validador.CNPJ)
                            .build();
                }
            };
        }
    },

    TELEFONE("Telefone", "Digite um telefone válido") {
        @Override
        public WatcherFragment buildFragment() {
            return new WatcherFragment() {
                @Override
                protected TextWatcher getWatcher(FragmentActivity activity) {
                    return new TelefoneTextWatcher(new SampleEventoDeValidacao(activity));
                }
            };
        }
    },

    CPF_CNPJ("CPF e CNPJ", "Digite um CPF ou CNPJ válido") {
        @Override
        public WatcherFragment buildFragment() {
            return new WatcherFragment() {
                @Override
                protected TextWatcher getWatcher(FragmentActivity activity) {
                    return new CPFCNPJTextWatcher(new SampleEventoDeValidacao(activity));
                }
            };
        }
    },

    CEP("CEP", "Digite um CEP válido") {
        @Override
        public WatcherFragment buildFragment() {
            return new WatcherFragment() {
                @Override
                protected TextWatcher getWatcher(FragmentActivity activity) {
                    return new CEPTextWatcher(new SampleEventoDeValidacao(activity));
                }
            };
        }
    },

    MASCARA_GENERICA("Máscara genérica", "5 números") {
        @Override
        public WatcherFragment buildFragment() {
            return new WatcherFragment() {
                @Override
                protected TextWatcher getWatcher(FragmentActivity activity) {
                    return new MascaraNumericaTextWatcher.Builder()
                            .paraMascara("#-#-#-#-#")
                            .build();
                }
            };
        }
    },

    VALOR_MONETARIO("Valor monetário", "Digite um valor monetário") {
        @Override
        public CanarinhoValorMonetarioWatcherFragment buildFragment() {
            return new CanarinhoValorMonetarioWatcherFragment();
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

    public static class SampleEventoDeValidacao implements EventoDeValidacao {

        protected TextInputLayout textInputLayout;
        private Context context;

        public SampleEventoDeValidacao(Context context) {
            this.context = context;
        }

        @Override
        public void invalido(String valorAtual, String mensagem) {
            textInputLayout.setError(mensagem);
        }

        @Override
        public void parcialmenteValido(String valorAtual) {
            textInputLayout.setError(null);
        }

        @Override
        public void totalmenteValido(String valorAtual) {
            new AlertDialog.Builder(context)
                    .setTitle("Campo válido!")
                    .setMessage(valorAtual)
                    .show();
        }

        public void setTextInputLayout(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }
    }

    public static class EventoDeValidacaoBoleto extends SampleEventoDeValidacao implements EventoDeValidacaoDeBoleto {

        public EventoDeValidacaoBoleto(Context context) {
            super(context);
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
