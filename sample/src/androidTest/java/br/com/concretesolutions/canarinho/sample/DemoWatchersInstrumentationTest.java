package br.com.concretesolutions.canarinho.sample;

import android.os.Build;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.concretesolutions.canarinho.sample.ui.activity.MainActivity;
import br.com.concretesolutions.canarinho.sample.ui.model.Watchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

@RunWith(AndroidJUnit4.class)
public class DemoWatchersInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void consegueDigitarUmBoletoNormalValido() throws InterruptedException {

        navigateToTab(Watchers.BOLETO_BANCARIO);

        Thread.sleep(1000L);

        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(typeText("34199310130010011560900500990007800000000000000"));

        onView(withText("Campo válido!")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueValidarUmBoletoSetandoOCodigoInteiro() throws InterruptedException {

        navigateToTab(Watchers.BOLETO_BANCARIO);

        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(paste("34199310130010011560900500990007800000000000000"));

        Thread.sleep(1000L);

        onView(withText("Campo válido!")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueValidarUmBoletoTributoSetandoOCodigoInteiro() throws InterruptedException {

        navigateToTab(Watchers.BOLETO_BANCARIO);

        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(paste("812345678901812345678901812345678901812345678901"));

        Thread.sleep(1000L);

        onView(withText("Campo válido!")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmBoletoNormalComBlocosInvalidos() throws InterruptedException {

        navigateToTab(Watchers.BOLETO_BANCARIO);

        // primeiro
        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(typeText("23790125016"));
        onView(withText("Primeiro bloco inválido")).check(matches(isDisplayed()));

        // segundo
        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(clearText(), typeText("2379012301600000030054"));
        onView(withText("Segundo bloco inválido")).check(matches(isDisplayed()));

        // terceiro
        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(clearText(), typeText("23790123016000000005325000456708"));
        onView(withText("Terceiro bloco inválido")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmBoletoTributoValido() throws InterruptedException {

        navigateToTab(Watchers.BOLETO_BANCARIO);

        // Boleto válido
        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(typeText("848600000015523301622010506101307129620012111220"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());

        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(clearText(), typeText("836600000019078800481000998854924516001265611135"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());
    }

    @Test
    public void consegueDigitarUmBoletoNormalComBlocosInvalidosComMensagemCustomizada() throws InterruptedException {

        navigateToTab(Watchers.BOLETO_BANCARIO_MSG_CUSTOM);

        // primeiro
        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(typeText("23790125016"));
        onView(withText("Primeira mensagem")).check(matches(isDisplayed()));

        // segundo
        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(clearText(), typeText("2379012301600000030054"));
        onView(withText("Segunda mensagem")).check(matches(isDisplayed()));

        // terceiro
        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(clearText(), typeText("23790123016000000005325000456708"));
        onView(withText("Terceira mensagem")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmCPFValido() throws InterruptedException {

        navigateToTab(Watchers.CPF);

        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(typeText("46574356636"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());
    }

    @Test
    public void consegueDigitarUmCPFInvalido() throws InterruptedException {

        navigateToTab(Watchers.CPF);

        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(typeText("46574356637"));
        onView(withText("CPF inválido")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmCNPJValido() throws InterruptedException {

        navigateToTab(Watchers.CNPJ);

        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(typeText("95621433000170"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());
    }

    @Test
    public void consegueDigitarUmCNPJInvalido() throws InterruptedException {

        navigateToTab(Watchers.CNPJ);
        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(typeText("95621433000180"));
        onView(withText("CNPJ inválido")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmTelefoneValido() throws InterruptedException {

        navigateToTab(Watchers.TELEFONE);

        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(typeText("1112345678"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());

        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(typeText("11123456789"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());
    }

    @Test
    public void consegueDigitarUmValorMonetarioFormatado() throws Throwable {

        navigateToTab(Watchers.VALOR_MONETARIO);

        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(typeText("1"))
                .check(matches(withText("0,01"))) // inicia com 0,0X
                .perform(typeText("2"))
                .check(matches(withText("0,12"))) // 0,XY
                .perform(typeText("3"))
                .check(matches(withText("1,23"))) // X,YZ
                .perform(typeText("4"))
                .check(matches(withText("12,34"))) // etc, etc, etc
                .perform(typeText("5"))
                .check(matches(withText("123,45")))
                .perform(typeText("6"))
                .check(matches(withText("1.234,56")))
                .perform(typeText("7"))
                .check(matches(withText("12.345,67")))
                .perform(typeText("8"))
                .check(matches(withText("123.456,78")))
                .perform(typeText("9"))
                .check(matches(withText("1.234.567,89")));
    }

    @Test
    public void consegueDigitarCPFCNPJValido() throws Throwable {

        navigateToTab(Watchers.CPF_CNPJ);

        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(typeText("46574356636"));
        onView(withText("Campo válido!")).check(matches(isDisplayed())).perform(pressBack());
        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(clearText(), typeText("95621433000170"));
        onView(withText("Campo válido!")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarCPFCNPJInvalido() throws Throwable {

        navigateToTab(Watchers.CPF_CNPJ);

        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(typeText("46574356637"));
        onView(withText("CPF inválido")).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(clearText(), typeText("15621433000180"));
        onView(withText("CNPJ inválido")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmCEPValido() throws Throwable {

        navigateToTab(Watchers.CEP);

        onView(allOf(withId(R.id.edit_text), isDisplayed())).perform(typeText("49025090"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void consegueUtilizarUmaMascaraGenericaSemValidadorOuEvento() throws Throwable {

        navigateToTab(Watchers.MASCARA_GENERICA);

        onView(allOf(withId(R.id.edit_text), isDisplayed()))
                .perform(typeText("12345"))
                .check(matches(withText("1-2-3-4-5")));
    }

    private void navigateToTab(Watchers watcher) throws InterruptedException {
        onView(
                allOf(
                        withText(watcher.getTitle()),
                        isDescendantOfA(withId(R.id.tabs)))
        )
                .perform(scrollTo(), click());

        Thread.sleep(500L);
    }

    private ViewAction paste(final String type) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                // noinspection unchecked
                Matcher<View> matchers = allOf(isDisplayed());

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    return allOf(matchers, supportsInputMethods());
                } else {
                    // SearchView does not support input methods itself (rather it delegates to an internal text
                    // view for input).
                    return allOf(matchers, anyOf(supportsInputMethods(), isAssignableFrom(SearchView.class)));
                }
            }

            @Override
            public String getDescription() {
                return "Straight typing into view";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((EditText) view).setText(type);
            }
        };
    }
}
