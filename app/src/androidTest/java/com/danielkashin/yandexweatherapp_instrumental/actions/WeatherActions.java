package com.danielkashin.yandexweatherapp_instrumental.actions;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp_instrumental.testenvironment.matchers.ToastMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * 
 */
public    class WeatherActions   {

    public void checkWeatherIsUpdated(String expectedTempCel) {
        onView(withId(R.id.text_main_temperature)).check(matches(isDisplayed()));
        onView(withId(R.id.text_main_temperature)).check(matches(withText(expectedTempCel)));
    }

    public void updateWeather() {
        onView(withId(R.id.image_refresh)).perform(click());
    }

    public void checkToast(String expectedTest) {
        onView(withText(expectedTest)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
