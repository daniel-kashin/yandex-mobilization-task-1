package com.danielkashin.yandexweatherapp_instrumental;

import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.presentation.view.application.YandexWeatherApp;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.MainDrawerActivity;
import com.danielkashin.yandexweatherapp_instrumental.actions.CustomDrawerActions;
import com.danielkashin.yandexweatherapp_instrumental.actions.SettingsActions;
import com.danielkashin.yandexweatherapp_instrumental.actions.WeatherActions;
import com.danielkashin.yandexweatherapp_instrumental.rules.AsyncTaskRule;
import com.danielkashin.yandexweatherapp_instrumental.rules.MockWebServerRule;
import com.danielkashin.yandexweatherapp_instrumental.rules.NeedsMockWebServer;
import com.danielkashin.yandexweatherapp_instrumental.testenvironment.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.danielkashin.yandexweatherapp.R.string.degree;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class MainDrawerActivityTest {

    private String expectedTempCel;
    private String expectedTempFar;
    private String settingDegree;
    private String settingInterval;
    private String settingCity;
    private String errorInternet;
    private String[] periods;
    @Rule
    public RuleChain rules = RuleChain.emptyRuleChain()
            .around(new MockWebServerRule(this))
            .around(new AsyncTaskRule())
            .around(new ActivityTestRule<>(MainDrawerActivity.class));

    @Before
    public void setUp() throws Exception {
        YandexWeatherApp app = TestUtils.app();
        expectedTempCel = TestContract.EXPECTED_MAIN_TEMPERATURE_CEL+app.getString(degree);
        expectedTempFar = TestContract.EXPECTED_MAIN_TEMPERATURE_FAR+app.getString(degree);
        settingDegree = app.getString(R.string.fahrenheit_degree);
        settingInterval = app.getString(R.string.refresh_interval);
        settingCity = app.getString(R.string.select_city);
        errorInternet = app.getString(R.string.no_internet_connection);
        periods = app.getResources().getStringArray(R.array.refresh_intervals_identifiers);
    }

    @Test
    public void testDrawerNavigation(){
        CustomDrawerActions drawerActions = new CustomDrawerActions();

        drawerActions.navigateToSettings();
        pressBack();
        drawerActions.navigateToAbout();
        pressBack();
    }

    @Test
    @NeedsMockWebServer(setupMethod = "successResponse_setupMockWebServer")
    public void successResponse(){
        WeatherActions weatherActions = new WeatherActions();

        weatherActions.updateWeather();
        weatherActions.checkWeatherIsUpdated(expectedTempCel);
    }

    @Test
    @NeedsMockWebServer(setupMethod = "errorResponse_setupMockWebServer")
    public void errorResponse(){
        WeatherActions weatherActions = new WeatherActions();
        weatherActions.updateWeather();
        weatherActions.checkToast(errorInternet);
    }

    @Test
    @NeedsMockWebServer(setupMethod = "updateCelInFarResponse_setupMockWebServer")
    public void updateCelInFarResponse(){
        CustomDrawerActions drawerActions = new CustomDrawerActions();
        SettingsActions settingsActions = new SettingsActions();

        drawerActions.navigateToSettings();
        freezeThread(1000);
        settingsActions.clickOnSetting(settingDegree);

        pressBack();

        onView(withId(R.id.text_main_temperature)).check(matches(withText(expectedTempFar)));
    }

    @Test
    public void testReschedule(){
        CustomDrawerActions drawerActions = new CustomDrawerActions();
        SettingsActions settingsActions = new SettingsActions();

        drawerActions.navigateToSettings();
        freezeThread(1000);
        settingsActions.clickOnSetting(settingInterval);
        settingsActions.clickOnInterval(periods[1]);
        settingsActions.clickOnSetting(settingInterval);
        settingsActions.checkInterval(periods[1]);
        pressBack();

    }

    public void successResponse_setupMockWebServer(@NonNull MockWebServer mockWebServer){
        mockWebServer.enqueue(new MockResponse().setBody(TestContract.createResponse())); //for first launch
        mockWebServer.enqueue(new MockResponse().setBody(TestContract.createResponse())); //for update
    }

    public void errorResponse_setupMockWebServer(@NonNull MockWebServer mockWebServer){
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));
    }

    public void updateCelInFarResponse_setupMockWebServer(@NonNull MockWebServer mockWebServer){
        mockWebServer.enqueue(new MockResponse().setBody(TestContract.createResponse()));
        mockWebServer.enqueue(new MockResponse().setBody(TestContract.createResponse()));
    }
    //When Idling is not available
    private void freezeThread(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
