package br.com.concrete.canarinho.test.watcher;

import android.app.Activity;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.robolectric.android.controller.ActivityController;

import br.com.concrete.canarinho.watcher.ValorMonetarioWatcher;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.buildActivity;

@RunWith(AndroidJUnit4.class)
public class ValorMonetarioWatcherTest {

    private EditText editText;

    @Before
    public void setUp() {
        final ActivityController<Activity> activityController = buildActivity(Activity.class);
        final Activity activity = activityController.create().get();
        activityController.start().resume().visible();

        TextInputLayout textInputLayout;
        activity.setContentView(textInputLayout = new TextInputLayout(activity));
        textInputLayout.addView(editText = new EditText(activity));
        editText.setText("0,00");
    }

    @Test
    public void watcher_formataOk() {
        editText.addTextChangedListener(new ValorMonetarioWatcher());
        editText.append("1234567890");
        assertThat(editText.getText().toString(), is("12.345.678,90"));
    }

    @Test
    public void watcher_formataOkComSimbolo() {
        editText.addTextChangedListener(new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .comMantemZerosAoLimpar()
                .build());
        editText.append("1234567890");
        assertThat(editText.getText().toString(), is("R$ 12.345.678,90"));
    }

    @Test
    public void watcher_canEmptyTextAndKeepZeroes() {
        editText.addTextChangedListener(new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .comMantemZerosAoLimpar()
                .build());
        editText.append("1234567890");
        assertThat(editText.getText().toString(), is("R$ 12.345.678,90"));

        editText.getText().clear();
        assertThat(editText.getText().toString(), is("R$ 0,00"));

        editText.getText().append('1');
        assertThat(editText.getText().toString(), is("R$ 0,01"));
    }

    @Test
    public void watcher_canEmptyTextWithoutZeroes() {
        editText.addTextChangedListener(new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .build());
        editText.append("1234567890");
        assertThat(editText.getText().toString(), is("R$ 12.345.678,90"));

        editText.getText().clear();
        assertThat(editText.getText().toString(), is(""));

        editText.getText().append('1');
        assertThat(editText.getText().toString(), is("R$ 0,01"));
    }
}
