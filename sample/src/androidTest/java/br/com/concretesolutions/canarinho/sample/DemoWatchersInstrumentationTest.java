package br.com.concretesolutions.canarinho.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DemoWatchersInstrumentationTest {

    @Rule
    public ActivityTestRule<DemoWatchersActivity> rule = new ActivityTestRule<>(DemoWatchersActivity.class);

    @Test
    public void consegueDigitarUmBoletoNormalValido() throws InterruptedException {

        // Boleto válido
        onView(withId(R.id.edit_boleto))
                .perform(typeText("23790123016000000005325000456704964680000013580"));

        Thread.sleep(1500L);

        onView(withText("Campo válido!")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueValidarUmBoletoSetandoOCodigoInteiro() throws InterruptedException {

        final DemoWatchersActivity activity = rule.getActivity();
        final TextView viewById = (TextView) activity.findViewById(R.id.edit_boleto);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewById.setText("34199310130010011560900500990007800000000000000");
            }
        });

        Thread.sleep(1500L);

        onView(withText("Campo válido!")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmBoletoNormalComBlocosInvalidos() throws InterruptedException {

        // primeiro
        onView(withId(R.id.edit_boleto)).perform(typeText("23790125016"));
        onView(withText("Primeiro bloco inválido")).check(matches(isDisplayed()));

        // segundo
        onView(withId(R.id.edit_boleto)).perform(clearText(), typeText("2379012301600000030054"));
        onView(withText("Segundo bloco inválido")).check(matches(isDisplayed()));

        // terceiro
        onView(withId(R.id.edit_boleto)).perform(clearText(), typeText("23790123016000000005325000456708"));
        onView(withText("Terceiro bloco inválido")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmBoletoTributoValido() {

        // Boleto válido
        onView(withId(R.id.edit_boleto))
                .perform(typeText("848600000015523301622010506101307129620012111220"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());

        onView(withId(R.id.edit_boleto))
                .perform(clearText(), typeText("836600000019078800481000998854924516001265611135"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());
    }

    @Test
    public void consegueDigitarUmCPFValido() {

        onView(withId(R.id.edit_cpf)).perform(typeText("46574356636"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());
    }

    @Test
    public void consegueDigitarUmCPFInvalido() {

        onView(withId(R.id.edit_cpf)).perform(typeText("46574356637"));
        onView(withText("CPF inválido")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmCNPJValido() {

        onView(withId(R.id.edit_cnpj)).perform(typeText("95621433000170"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());
    }

    @Test
    public void consegueDigitarUmCNPJInvalido() {

        onView(withId(R.id.edit_cnpj)).perform(typeText("95621433000180"));
        onView(withText("CNPJ inválido")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmTelefoneValido() {
        onView(withId(R.id.edit_telefone)).perform(typeText("1112345678"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());

        onView(withId(R.id.edit_telefone)).perform(typeText("11123456789"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()))
                .perform(pressBack());
    }

    @Test
    public void consegueDigitarUmValorMonetarioFormatado() throws Throwable {

        final NestedScrollView scroll = (NestedScrollView) rule.getActivity().findViewById(R.id.container);

        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

        Thread.sleep(2000L);

        onView(withId(R.id.edit_valor))
                .perform(swipeUp(), typeText("1"))
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

        final NestedScrollView scroll = (NestedScrollView) rule.getActivity().findViewById(R.id.container);

        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

        Thread.sleep(2000L);

        onView(withId(R.id.edit_cpf_cnpj)).perform(swipeUp(), typeText("46574356636"));
        onView(withText("Campo válido!")).check(matches(isDisplayed())).perform(pressBack());
        onView(withId(R.id.edit_cpf_cnpj)).perform(clearText(), typeText("95621433000170"));
        onView(withText("Campo válido!")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarCPFCNPJInvalido() throws Throwable {
        final NestedScrollView scroll = (NestedScrollView) rule.getActivity().findViewById(R.id.container);

        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

        Thread.sleep(2000L);

        onView(withId(R.id.edit_cpf_cnpj)).perform(swipeUp(), typeText("46574356637"));
        onView(withText("CPF inválido")).check(matches(isDisplayed()));

        onView(withId(R.id.edit_cpf_cnpj)).perform(clearText(), typeText("95621433000180"));
        onView(withText("CNPJ inválido")).check(matches(isDisplayed()));
    }

    @Test
    public void consegueDigitarUmCEPValido() throws Throwable {

        final NestedScrollView scroll = (NestedScrollView) rule.getActivity().findViewById(R.id.container);

        rule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

        Thread.sleep(2000L);
        onView(withId(R.id.edit_cep)).perform(typeText("49025090"));

        onView(withText("Campo válido!"))
                .check(matches(isDisplayed()));
    }
}