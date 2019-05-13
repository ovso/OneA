package io.github.ovso.onea.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.gun0912.tedpermission.util.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static android.content.Context.TELEPHONY_SERVICE;

public final class SimOperator {

  public static String getOperator(Context context) {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
    if (ObjectUtils.isEmpty(telephonyManager)) {
      return "";
    } else {
      return telephonyManager.getNetworkOperator();
    }
  }

  @AllArgsConstructor @Getter public enum Type {
    KT("KT"), SKT("SKT"), LGT("U+"), UNKNOWN("N/A");
    private String displayName;
  }

  public static Type toType(String operator) {
    if (ObjectUtils.isEmpty(operator)) {
      return Type.UNKNOWN;
    } else {
      switch (operator) {
        case "45008":
          return Type.KT;
        case "45005":
        case "45002":
          return Type.SKT;
        case "45006":
          return Type.LGT;
        default:
          return Type.UNKNOWN;
      }
    }
  }
}
