package br.com.concrete.canarinho.test.watcher;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import br.com.concrete.canarinho.watcher.BoletoBancarioTextWatcher;
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacaoDeBoleto;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class BoletoValidacaoBlocoTest {

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
                activity.setContentView(textInputLayout);
            }
        });
    }

    @Test
    public void blockValidation_canIdentifyMultipleInvalidBlocks() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                assertThat(blocoInvalido).containsExactly(1, 4);
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append("858440000090207203281837240720183202308757811045");
    }

    @Test
    public void blockValidation_dontNotifyCallbackWhenNoErrors() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                fail("Callback must not be invoked for this case");
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append("858200000090207203281837240720183202308757821035");
    }

    static abstract class EventoBase implements EventoDeValidacaoDeBoleto {

        @Override
        public abstract void invalido(String valorAtual, List<Integer> blocoInvalido);

        @Override
        public void invalido(String valorAtual, String mensagem) { }

        @Override
        public void parcialmenteValido(String valorAtual) { }

        @Override
        public void totalmenteValido(String valorAtual) { }
    }
}
