package io.github.ovso.onea.ui.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter public enum MarketType {
  ONE_STORE("com.skt.skaf.A000Z00040", "ONE Store"),
  GOOGLE_PLAY_STORE("com.android.vending", "Google play store"),
  GALAXY_APPS("com.sec.android.app.samsungapps", "Galaxy apps"),
  SAMSUNG_SMART_SWITCH("com.sec.android.easyMover.Agent", "Samsung Smart Switch"),
  ANDROID_PACKAGE_INSTALLER("com.google.android.packageinstaller`", "Android Package Installer"),
  SAMSUNG_MATE_AGENT("com.samsung.android.mateagent", "Samsung Mate Agent"),
  UNKNOWN("", "Unknown");

  private String pkg;
  private String name;

  public static MarketType toType(String marketPkg) {
    for (MarketType value : MarketType.values()) {
      if (value.pkg.equals(marketPkg)) {
        return value;
      }
    }
    return UNKNOWN;
  }
}

