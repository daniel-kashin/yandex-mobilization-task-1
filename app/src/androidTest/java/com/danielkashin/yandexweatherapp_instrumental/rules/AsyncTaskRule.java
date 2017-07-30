package com.danielkashin.yandexweatherapp_instrumental.rules;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import com.danielkashin.yandexweatherapp.data.entities.repository.Weather;
import com.danielkashin.yandexweatherapp.data.exceptions.ExceptionBundle;
import com.danielkashin.yandexweatherapp.domain.use_cases.GetWeatherUseCase;
import com.danielkashin.yandexweatherapp_instrumental.testenvironment.TestUtils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 */
public class AsyncTaskRule implements TestRule {

    @Override
    @NonNull
    public Statement apply(@NonNull Statement base, @NonNull Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final AsyncTaskRIdlingResource asyncTaskRIdlingResource = new AsyncTaskRIdlingResource(TestUtils.app().getWeatherComponent().weatherUseCase());

                Espresso.registerIdlingResources(asyncTaskRIdlingResource);
                base.evaluate();
            }
        };
    }

    static class AsyncTaskRIdlingResource implements IdlingResource {

        private final GetWeatherUseCase asyncTaskResponse;
        private GetWeatherUseCase.Callbacks listener;

        public AsyncTaskRIdlingResource(GetWeatherUseCase asyncTaskResponse) {
            this.asyncTaskResponse = asyncTaskResponse;
        }

        @Override
        public String getName() {
            return "AsyncTask-IdlingResource";
        }

        @Override
        public boolean isIdleNow() {
            return !asyncTaskResponse.isRunning();
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {

            if (listener != null) {
                asyncTaskResponse.removeListener(listener);
            }

            listener = new GetWeatherUseCase.Callbacks() {
                @Override
                public void onGetWeatherSuccess(Weather weather) {
                    callback.onTransitionToIdle();
                }

                @Override
                public void onGetWeatherException(ExceptionBundle exceptionBundle) {
                    callback.onTransitionToIdle();
                }
            };

            asyncTaskResponse.addAdditionalListener(listener);
        }
    }
}
