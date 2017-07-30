package com.danielkashin.yandexweatherapp_instrumental.testenvironment.matchers;

import android.os.IBinder;
import android.support.test.espresso.Root;
import android.view.WindowManager;

import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {


  @Override
  public boolean matchesSafely(Root root) {
    int type = root.getWindowLayoutParams().get().type;
    if (type == WindowManager.LayoutParams.TYPE_TOAST) {
        IBinder windowToken = root.getDecorView().getWindowToken();
        IBinder appToken = root.getDecorView().getApplicationWindowToken();
        if (windowToken == appToken) {
            // windowToken == appToken means this window isn't contained by any other windows.
            // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
            return true;
        }
    }
    return false;
  }

    @Override
    public void describeTo(org.hamcrest.Description description) {
        description.appendText("is toast");
    }
}