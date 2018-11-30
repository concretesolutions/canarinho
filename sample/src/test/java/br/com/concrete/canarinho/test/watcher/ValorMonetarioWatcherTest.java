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
import br.com.concrete.canarinho.watcher.ValorMonetarioWatcher;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class ValorMonetarioWatcherTest {

    private EditText editText;

    @Before
    public void setUp() {
        final ActivityScenario<AppCompatActivity> scenario = ActivityScenario.launch(AppCompatActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.onActivity(new ActivityScenario.ActivityAction<AppCompatActivity>() {
            @Override
            public void perform(AppCompatActivity activity) {
                TextInputLayout textInputLayout;
                activity.setContentView(textInputLayout = new TextInputLayout(activity));
                textInputLayout.addView(editText = new EditText(activity));
                editText.setText("0,00");
            }
        });
    }

    @Test
    public void watcher_formataOk() {
        editText.addTextChangedListener(new ValorMonetarioWatcher());
        editText.append("1234567890");
        assertThat(editText.getText().toString()).isEqualTo("12.345.678,90");
    }

    @Test
    public void watcher_formataOkComSimbolo() {
        editText.addTextChangedListener(new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .comMantemZerosAoLimpar()
                .build());
        editText.append("1234567890");
        assertThat(editText.getText().toString()).isEqualTo("R$ 12.345.678,90");
    }

    @Test
    public void watcher_canEmptyTextAndKeepZeroes() {
        editText.addTextChangedListener(new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .comMantemZerosAoLimpar()
                .build());
        editText.append("1234567890");
        assertThat(editText.getText().toString()).isEqualTo("R$ 12.345.678,90");

        editText.getText().clear();
        assertThat(editText.getText().toString()).isEqualTo("R$ 0,00");

        editText.getText().append('1');
        assertThat(editText.getText().toString()).isEqualTo("R$ 0,01");
    }

    @Test
    public void watcher_canEmptyTextWithoutZeroes() {
        editText.addTextChangedListener(new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .build());
        editText.append("1234567890");
        assertThat(editText.getText().toString()).isEqualTo("R$ 12.345.678,90");

        editText.getText().clear();
        assertThat(editText.getText().toString()).isEmpty();

        editText.getText().append('1');
        assertThat(editText.getText().toString()).isEqualTo("R$ 0,01");
    }
}
