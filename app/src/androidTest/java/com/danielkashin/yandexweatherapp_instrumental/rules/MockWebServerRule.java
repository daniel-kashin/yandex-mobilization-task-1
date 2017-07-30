package com.danielkashin.yandexweatherapp_instrumental.rules;

import android.support.annotation.NonNull;

import com.danielkashin.yandexweatherapp_instrumental.testenvironment.TestUtils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;

import okhttp3.mockwebserver.MockWebServer;

/**
 * JUnit test rule for mocking web server!
 */
public class MockWebServerRule implements TestRule {

    @NonNull
    private final Object testClassInstance;

    public MockWebServerRule(@NonNull Object testClassInstance) {
        this.testClassInstance = testClassInstance;
    }

    @Override
    @NonNull
    public Statement apply(@NonNull Statement base, @NonNull Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final NeedsMockWebServer needsMockWebServer = description.getAnnotation(NeedsMockWebServer.class);

                final MockWebServer mockWebServer = new MockWebServer();
                //Don't delete constant! Because dagger's graph will have only 1 port
                //It must not be dynamic
                mockWebServer.start(35654);
                String baseTestUrl = mockWebServer.url("").toString();
                TestUtils.app().getApplicationComponent().changeableBaseUrl().setBaseUrl(baseTestUrl);
                if (needsMockWebServer != null &&!needsMockWebServer.setupMethod().isEmpty()) {
                    final Method setupMethod = testClassInstance.getClass().getDeclaredMethod(needsMockWebServer.setupMethod(), MockWebServer.class);
                    setupMethod.invoke(testClassInstance, mockWebServer);
                }

                // Try to evaluate the test and anyway shutdown the MockWebServer.
                try {
                    base.evaluate();
                } finally {
                    mockWebServer.shutdown();
                }

            }
        };
    }
}
