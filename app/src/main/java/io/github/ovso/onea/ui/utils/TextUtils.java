package io.github.ovso.onea.ui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextUtils {
  private TextUtils() {
  }

  public static boolean isValidEmail(String email) {
    String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(email);
    return m.matches();
  }
}