package com.danielkashin.yandexweatherapp_instrumental.actions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 *
 */
public class SettingsActions {
    public void clickOnSetting(String text) {
        onView(withText(text)).perform(click());
    }

    public void clickOnInterval(String period) {
        onView(withText(period)).perform(click());
    }

    public void checkInterval(String period) {
        onView(withText(period)).check(matches(isChecked()));
    }
}
