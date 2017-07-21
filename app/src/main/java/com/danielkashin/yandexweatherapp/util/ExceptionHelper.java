package com.danielkashin.yandexweatherapp.util;


@SuppressWarnings("WeakerAccess") // methods with custom message can be used later
public class ExceptionHelper {

  private static final String MESSAGE_OBJECTS_NULL = "All objects must not be null";

  private static final String FALSE_CONDITION = "False condition";


  public static void checkAllObjectsNonNull(final Object... objects) {
    checkAllObjectsNonNull(MESSAGE_OBJECTS_NULL, objects);
  }

  public static void checkAllObjectsNonNull(String exceptionMessage, final Object... objects) {
    for (Object object : objects) {
      if (object == null) {
        throw new NullPointerException(exceptionMessage);
      }
    }
  }

  public static void assertFalse(boolean condition) {
    assertFalse(FALSE_CONDITION, condition);
  }

  public static void assertFalse(String exceptionMessage, boolean condition) {
    if (condition) {
      throw new IllegalStateException(exceptionMessage);
    }
  }

  public static void assertTrue(boolean condition) {
    assertTrue(FALSE_CONDITION, condition);
  }

  public static void assertTrue(String exceptionMessage, boolean condition) {
    if (!condition) {
      throw new IllegalStateException(exceptionMessage);
    }
  }

}