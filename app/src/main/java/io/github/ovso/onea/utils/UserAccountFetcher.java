package io.github.ovso.onea.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserAccountFetcher {

  public static String getEmail(Context context) {
    AccountManager accountManager = AccountManager.get(context);
    Account account = getAccount(accountManager);

    if (account == null) {
      return null;
    } else {
      return account.name;
    }
  }

  private static Account getAccount(AccountManager accountManager) {
    Account[] accounts = accountManager.getAccountsByType("com.google");
    Account account;
    if (accounts.length > 0) {
      account = accounts[0];
    } else {
      account = null;
    }
    return account;
  }

  public static boolean isValidEmail(String email) {
    String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(email);
    return m.matches();
  }
}
