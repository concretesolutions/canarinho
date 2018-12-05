package br.com.concrete.canarinho.test.watcher;

import android.text.style.ForegroundColorSpan;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class BoletoValidacaoBlocoTest {

    private EditText editText;
    private final String validBarCode = "00190000090050201018400016324188977340000015000";
    private final String validBarCodeTribute = "858200000090207203281837240720183202308757821035";
    private final String invalidBarCode = "00172001090050201018400033364188977340000015000";
    private final String invalidBarCodeTribute = "858440000090207203281837240720183202308757811045";
    private final String invalidBarCodeTributeFirstBlock = "858200000010207203281837240720183202308757821035";
    private final String partiallyValidBarCode = "0019000009";
    private final String partiallyValidBarCodeTribute = "858200000090";
    private final String partiallyInvalidBarCode = "0019000001";
    private final String partiallyInvalidBarCodeTribute = "8736451937450";

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
    public void blockValidationTribute_canIdentifyMultipleInvalidBlocks() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                assertThat(blocoInvalido).containsExactly(1, 4);
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(invalidBarCodeTribute);
    }

    @Test
    public void blockValidation_canIdentifyMultipleInvalidBlocks() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                assertThat(blocoInvalido).containsExactly(1, 3);
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(invalidBarCode);
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
        editText.append(validBarCode);
    }

    @Test
    public void blockValidationTribute_dontNotifyCallbackWhenNoErrors() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                fail("Callback must not be invoked for this case");
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(validBarCodeTribute);
    }

    @Test
    public void blockValidationTribute_hasMultipleBlockIsUnderline() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                ForegroundColorSpan[] spans = editText.getEditableText().getSpans(0,
                        editText.getEditableText().length(), ForegroundColorSpan.class);
                assertThat(blocoInvalido).containsExactly(1, 4);
                assertThat(spans).hasLength(2);
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(invalidBarCodeTribute);
    }

    @Test
    public void blockValidation_hasMultipleBlockIsUnderline() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                ForegroundColorSpan[] spans = editText.getEditableText().getSpans(0,
                        editText.getEditableText().length(), ForegroundColorSpan.class);
                assertThat(blocoInvalido).containsExactly(1, 3);
                assertThat(spans).hasLength(2);
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(invalidBarCode);
    }

    @Test
    public void blockValidationTribute_dontUnderlineWhenNoErrors() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                fail("Callback must not be invoked for this case");
            }

            @Override
            public void totalmenteValido(String valorAtual) {
                super.totalmenteValido(valorAtual);
                ForegroundColorSpan[] spans = editText.getEditableText().getSpans(0,
                        editText.getEditableText().length(), ForegroundColorSpan.class);
                assertThat(spans).hasLength(0);
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(validBarCodeTribute);
    }

    @Test
    public void blockValidation_dontUnderlineWhenNoErrors() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                fail("Callback must not be invoked for this case");
            }

            @Override
            public void totalmenteValido(String valorAtual) {
                super.totalmenteValido(valorAtual);
                ForegroundColorSpan[] spans = editText.getEditableText().getSpans(0,
                        editText.getEditableText().length(), ForegroundColorSpan.class);
                assertThat(spans).hasLength(0);
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(validBarCode);
    }

    // Esse:::
    @Test
    public void blockValidation_dontHasUnderlineOnPartiallyValid() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                fail("Callback must not be invoked for this case");
            }

            @Override
            public void parcialmenteValido(String valorAtual) {
                super.parcialmenteValido(valorAtual);
                ForegroundColorSpan[] spans = editText.getEditableText().getSpans(0,
                        editText.getEditableText().length(), ForegroundColorSpan.class);
                assertThat(spans).hasLength(0);
            }

            @Override
            public void totalmenteValido(String valorAtual) {
                super.totalmenteValido(valorAtual);
                fail("Callback must not be invoked for this case");
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(partiallyValidBarCode);
    }

    @Test
    public void blockValidationTribute_dontHasUnderlineOnPartiallyValid() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                fail("Callback must not be invoked for this case");
            }

            @Override
            public void parcialmenteValido(String valorAtual) {
                super.parcialmenteValido(valorAtual);
                ForegroundColorSpan[] spans = editText.getEditableText().getSpans(0,
                        editText.getEditableText().length(), ForegroundColorSpan.class);
                assertThat(spans).hasLength(0);
            }

        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(partiallyValidBarCodeTribute);
    }

    @Test
    public void blockValidation_hasUnderlineOnPartiallyInValid() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                ForegroundColorSpan[] spans = editText.getEditableText().getSpans(0,
                        editText.getEditableText().length(), ForegroundColorSpan.class);
                assertTrue(spans.length > 0);
            }

            @Override
            public void parcialmenteValido(String valorAtual) {
                super.parcialmenteValido(valorAtual);
                fail("Callback must not be invoked for this case");
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(partiallyInvalidBarCode);
    }

    @Test
    public void blockValidationTribute_hasUnderlineOnPartiallyInValid() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) {
                ForegroundColorSpan[] spans = editText.getEditableText().getSpans(0,
                        editText.getEditableText().length(), ForegroundColorSpan.class);
                assertTrue(spans.length > 0);
            }

            @Override
            public void parcialmenteValido(String valorAtual) {
                super.parcialmenteValido(valorAtual);
                fail("Callback must not be invoked for this case");
            }
        };

        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(partiallyInvalidBarCodeTribute);
    }

    @Test
    public void blockValidation_checkMensageForInvalidBlock() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) { }

            @Override
            public void invalido(String valorAtual, String mensagem) {
                super.invalido(valorAtual, mensagem);
                assertEquals("Primeiro, Terceiro bloco(s) inválido(s)", mensagem);
            }
        };
        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(invalidBarCode);
    }

    @Test
    public void blockValidationTribute_checkMensageForInvalidBlock() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) { }

            @Override
            public void invalido(String valorAtual, String mensagem) {
                super.invalido(valorAtual, mensagem);
                assertEquals("Primeiro bloco(s) inválido(s)", mensagem);
            }
        };
        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(invalidBarCodeTributeFirstBlock);
    }

    @Test
    public void blockValidation_checkMensageForValidBlock() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) { }

            @Override
            public void invalido(String valorAtual, String mensagem) {
                super.invalido(valorAtual, mensagem);
                assertEquals("", mensagem);
            }
        };
        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(validBarCode);
    }

    @Test
    public void blockValidationTribute_checkMensageForValidBlock() {
        EventoBase callback = new EventoBase() {
            @Override
            public void invalido(String valorAtual, List<Integer> blocoInvalido) { }

            @Override
            public void invalido(String valorAtual, String mensagem) {
                super.invalido(valorAtual, mensagem);
                assertEquals("", mensagem);
            }
        };
        editText.addTextChangedListener(new BoletoBancarioTextWatcher(callback));
        editText.append(validBarCodeTribute);
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
