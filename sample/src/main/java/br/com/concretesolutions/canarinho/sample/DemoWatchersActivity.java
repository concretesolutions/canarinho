package br.com.concretesolutions.canarinho.sample;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import br.com.concretesolutions.canarinho.validator.Validador;
import br.com.concretesolutions.canarinho.watcher.BoletoBancarioTextWatcher;
import br.com.concretesolutions.canarinho.watcher.CEPTextWatcher;
import br.com.concretesolutions.canarinho.watcher.CPFCNPJTextWatcher;
import br.com.concretesolutions.canarinho.watcher.MascaraNumericaTextWatcher;
import br.com.concretesolutions.canarinho.watcher.TelefoneTextWatcher;
import br.com.concretesolutions.canarinho.watcher.ValorMonetarioWatcher;
import br.com.concretesolutions.canarinho.watcher.evento.EventoDeValidacao;

/** */
public class DemoWatchersActivity extends AppCompatActivity {

    private EditText editBoleto;
    private EditText editCPF;
    private EditText editCNPJ;
    private EditText editTelefone;
    private EditText editValor;
    private EditText editCPFCNPJ;
    private EditText editCEP;

    private TextInputLayout editLayoutBoleto;
    private TextInputLayout editLayoutCPF;
    private TextInputLayout editLayoutCNPJ;
    private TextInputLayout editLayoutTelefone;
    private TextInputLayout editLayoutCPFCNPJ;
    private TextInputLayout editLayoutCEP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_watcher_activity);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editBoleto = (EditText) findViewById(R.id.edit_boleto);
        editCPF = (EditText) findViewById(R.id.edit_cpf);
        editCNPJ = (EditText) findViewById(R.id.edit_cnpj);
        editTelefone = (EditText) findViewById(R.id.edit_telefone);
        editValor = (EditText) findViewById(R.id.edit_valor);
        editCPFCNPJ = (EditText) findViewById(R.id.edit_cpf_cnpj);
        editCEP = (EditText) findViewById(R.id.edit_cep);

        editLayoutBoleto = (TextInputLayout) findViewById(R.id.edit_layout_boleto);
        editLayoutCPF = (TextInputLayout) findViewById(R.id.edit_layout_cpf);
        editLayoutCNPJ = (TextInputLayout) findViewById(R.id.edit_layout_cnpj);
        editLayoutTelefone = (TextInputLayout) findViewById(R.id.edit_layout_telefone);
        editLayoutCPFCNPJ = (TextInputLayout) findViewById(R.id.edit_layout_cpf_cnpj);
        editLayoutCEP = (TextInputLayout) findViewById(R.id.edit_layout_cep);

        editBoleto.addTextChangedListener(new BoletoBancarioTextWatcher(geraParaCampo(editLayoutBoleto)));
        editCPF.addTextChangedListener(new MascaraNumericaTextWatcher.Builder()
                .paraMascara("###.###.###-##")
                .comCallbackDeValidacao(geraParaCampo(editLayoutCPF))
                .comValidador(Validador.CPF)
                .build());
        editCNPJ.addTextChangedListener(new MascaraNumericaTextWatcher.Builder()
                .paraMascara("##.###.###/####-##")
                .comCallbackDeValidacao(geraParaCampo(editLayoutCNPJ))
                .comValidador(Validador.CNPJ)
                .build());
        editTelefone.addTextChangedListener(new TelefoneTextWatcher(geraParaCampo(editLayoutTelefone)));
        editValor.addTextChangedListener(new ValorMonetarioWatcher());
        editCPFCNPJ.addTextChangedListener(new CPFCNPJTextWatcher(geraParaCampo(editLayoutCPFCNPJ)));
        editCEP.addTextChangedListener(new CEPTextWatcher(geraParaCampo(editLayoutCEP)));
    }

    private EventoDeValidacao geraParaCampo(final TextInputLayout campo) {

        return new EventoDeValidacao() {

            @Override
            public void invalido(String valorAtual, String mensagem) {
                campo.setError(mensagem);
            }

            @Override
            public void parcialmenteValido(String valorAtual) {
                campo.setError(null);
            }

            @Override
            public void totalmenteValido(String valorAtual) {
                new AlertDialog.Builder(DemoWatchersActivity.this)
                        .setTitle("Campo válido!")
                        .setMessage(valorAtual)
                        .show();
            }
        };
    }
}
