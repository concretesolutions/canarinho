package br.com.concrete.canarinho.test.watcher;

import android.app.Activity;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import br.com.concrete.canarinho.sample.ui.model.Watchers;
import br.com.concrete.canarinho.watcher.BoletoBancarioTextWatcher;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class BoletoTextWatcherTest {

    private BoletoBancarioTextWatcher watcher;
    private EditText editText;

    @Before
    public void setUp() {
        final ActivityScenario<Activity> scenario = ActivityScenario.launch(Activity.class);
        scenario.onActivity(new ActivityScenario.ActivityAction<Activity>() {
            @Override
            public void perform(Activity activity) {
                final TextInputLayout textInputLayout = new TextInputLayout(activity);
                textInputLayout.addView(editText = new EditText(activity));

                final Watchers.SampleEventoDeValidacao sampleEventoDeValidacao =
                        new Watchers.SampleEventoDeValidacao(textInputLayout);

                editText.addTextChangedListener(watcher = new BoletoBancarioTextWatcher(sampleEventoDeValidacao));

                activity.setContentView(textInputLayout);
            }
        });
    }

    @Test
    public void typing_canValidateEmptyState() {
        editText.append("");
        assertThat(editText.getText().toString(), is(""));
        assertThat(watcher.getResultadoParcial().isParcialmenteValido(), is(true));
    }

    @Test
    public void typing_canValidateProperCharacters() {
        editText.append("1bas2nas3lamsd4");
        assertThat(editText.getText().toString(), is("1234"));
        assertThat(watcher.getResultadoParcial().isParcialmenteValido(), is(true));
    }

    @Test
    public void deleting_canEmptyEditText() {
        editText.append("1234");
        assertThat(editText.getText().toString(), is("1234"));
        assertThat(watcher.getResultadoParcial().isParcialmenteValido(), is(true));

        editText.getEditableText().clear();
        assertThat(editText.getText().toString(), is(""));
    }

    // Teste de regressão
    @Test
    public void deleting_afterEmptyingEditTextItKeepsValidatingInput() {
        editText.append("1234");
        assertThat(editText.getText().toString(), is("1234"));
        assertThat(watcher.getResultadoParcial().isParcialmenteValido(), is(true));

        editText.getEditableText().clear();
        assertThat(editText.getText().toString(), is(""));

        // menos caracteres que o tamanho inicial para saber qual máscara aplicar
        editText.append("$$");
        assertThat(editText.getText().toString(), is(""));
    }
}
