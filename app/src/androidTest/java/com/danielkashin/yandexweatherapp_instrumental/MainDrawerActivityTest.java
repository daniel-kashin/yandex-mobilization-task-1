package com.danielkashin.yandexweatherapp_instrumental;

import android.support.annotation.NonNull;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.MainDrawerActivity;
import com.danielkashin.yandexweatherapp_instrumental.testenvironment.matchers.ToastMatcher;
import com.danielkashin.yandexweatherapp_instrumental.testenvironment.rules.MockWebServerRule;
import com.danielkashin.yandexweatherapp_instrumental.testenvironment.rules.NeedsMockWebServer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class MainDrawerActivityTest {

    ActivityTestRule<MainDrawerActivity> activityRule =  new ActivityTestRule<>(MainDrawerActivity.class);

    @Rule
    public RuleChain rules = RuleChain.emptyRuleChain()
            .around(new MockWebServerRule(this))
            .around(activityRule);

    @Test
    public void testDrawerNavigation(){
        onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.navigation_settings));
        pressBack();

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.navigation_about));
        pressBack();

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.navigation_weather));
    }

    @Test
    @NeedsMockWebServer(setupMethod = "successResponse_setupMockWebServer")
    public void successResponse(){
        onView(withId(R.id.image_refresh)).perform(click());
        onView(withId(R.id.text_main_temperature)).check(matches(isDisplayed()));
    }

    public void successResponse_setupMockWebServer(@NonNull MockWebServer mockWebServer){
        Log.d("myLogs", "successResponse_setupMockWebServer: ");
        mockWebServer.enqueue(new MockResponse().setBody(TestContract.createResponse()));
        Log.d("myLogs", "successResponse_setupMockWebServer: " + mockWebServer.getRequestCount());
    }

    @Test
    @NeedsMockWebServer(setupMethod = "errorResponse_setupMockWebServer")
    public void errorResponse(){
        onView(withId(R.id.image_refresh)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkToast();
    }

    public void errorResponse_setupMockWebServer(@NonNull MockWebServer mockWebServer){
        mockWebServer.enqueue(new MockResponse().setBody("HTTP/1.1 500 Not today"));
        Log.d("myLogs", "errorResponse_setupMockWebServer: " + mockWebServer.getRequestCount());
    }

    public void checkToast() {
        onView(withText(R.string.no_internet_connection)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
