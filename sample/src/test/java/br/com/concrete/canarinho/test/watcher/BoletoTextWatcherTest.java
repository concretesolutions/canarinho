package br.com.concrete.canarinho.test.watcher;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import br.com.concrete.canarinho.sample.BuildConfig;
import br.com.concrete.canarinho.sample.ui.activity.MainActivity;
import br.com.concrete.canarinho.sample.ui.model.Watchers;
import br.com.concrete.canarinho.watcher.BoletoBancarioTextWatcher;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.buildActivity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class BoletoTextWatcherTest {

    private BoletoBancarioTextWatcher watcher;
    private EditText editText;

    @Before
    public void setUp() {

        final ActivityController<MainActivity> activityController = buildActivity(MainActivity.class);
        final Activity activity = activityController.create().get();

        final TextInputLayout textInputLayout = new TextInputLayout(activity);
        textInputLayout.addView(editText = new EditText(activity));
        activityController.start().resume().visible();

        final Watchers.SampleEventoDeValidacao sampleEventoDeValidacao =
                new Watchers.SampleEventoDeValidacao(textInputLayout);

        editText.addTextChangedListener(watcher = new BoletoBancarioTextWatcher(sampleEventoDeValidacao));

        activity.setContentView(textInputLayout);
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
