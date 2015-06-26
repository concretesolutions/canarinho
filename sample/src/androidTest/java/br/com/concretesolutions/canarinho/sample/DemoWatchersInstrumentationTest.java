package br.com.concretesolutions.canarinho.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DemoWatchersInstrumentationTest {

    @Rule
    public ActivityTestRule<DemoWatchersActivity> rule = new ActivityTestRule<>(DemoWatchersActivity.class);

    @Test
    public void consegueDigitarUmBoletoNormalValido() {

        // Boleto válido
        onView(withId(R.id.edit_boleto))
                .perform(typeText("23790123016000000005325000456704964680000013580"));

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
}