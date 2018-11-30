package br.com.concrete.canarinho.test.watcher;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import br.com.concrete.canarinho.sample.ui.model.Watchers;
import br.com.concrete.canarinho.watcher.BoletoBancarioTextWatcher;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class BoletoTextWatcherTest {

    private BoletoBancarioTextWatcher watcher;
    private EditText editText;

    @Before
    public void setUp() {
        final ActivityScenario<AppCompatActivity> scenario = ActivityScenario.launch(AppCompatActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.onActivity(new ActivityScenario.ActivityAction<AppCompatActivity>() {

            @Override
            public void perform(AppCompatActivity activity) {
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
        assertThat(editText.getText().toString()).isEmpty();
        assertThat(watcher.getResultadoParcial().isParcialmenteValido()).isTrue();
    }

    @Test
    public void typing_canValidateProperCharacters() {
        editText.append("1bas2nas3lamsd4");
        assertThat(editText.getText().toString()).isEqualTo("1234");
        assertThat(watcher.getResultadoParcial().isParcialmenteValido()).isTrue();
    }

    @Test
    public void deleting_canEmptyEditText() {
        editText.append("1234");
        assertThat(editText.getText().toString()).isEqualTo("1234");
        assertThat(watcher.getResultadoParcial().isParcialmenteValido()).isTrue();

        editText.getEditableText().clear();
        assertThat(editText.getText().toString()).isEmpty();
    }

    // Teste de regressão
    @Test
    public void deleting_afterEmptyingEditTextItKeepsValidatingInput() {
        editText.append("1234");
        assertThat(editText.getText().toString()).isEqualTo("1234");
        assertThat(watcher.getResultadoParcial().isParcialmenteValido()).isTrue();

        editText.getEditableText().clear();
        assertThat(editText.getText().toString()).isEmpty();

        // menos caracteres que o tamanho inicial para saber qual máscara aplicar
        editText.append("$$");
        assertThat(editText.getText().toString()).isEmpty();
    }
}
