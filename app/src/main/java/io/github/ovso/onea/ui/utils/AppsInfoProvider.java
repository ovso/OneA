package io.github.ovso.onea.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.io.File;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor public class AppsInfoProvider {
  public Context context;

  public long getApkSize(ResolveInfo info) {
    String sourceDir = info.activityInfo.applicationInfo.sourceDir;
    File file = new File(sourceDir);
    if (file.exists()) {
      return file.length();
    } else {
      return 0L;
    }
  }

  public long getLastUpdateTime(String pkg) {
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkg, 0);
      return packageInfo.lastUpdateTime;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return 0L;
    }
  }

  public String getApkPath(ResolveInfo info) {
    return info.activityInfo.applicationInfo.sourceDir;
  }

  public long getFirstInstallTime(String pkg) {
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkg, 0);
      return packageInfo.firstInstallTime;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return 0L;
    }
  }

  public String getAppVersion(String pkg) {
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkg, 0);
      return packageInfo.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return "";
    }
  }

  public String getFromInstalledMarket(String targetPkg) {
    PackageManager pkgMgr = context.getPackageManager();
    String marketPkg = pkgMgr.getInstallerPackageName(targetPkg);
    final MarketType type = MarketType.toType(marketPkg);
    return type.getName();
  }

  public List<ResolveInfo> getInstallApps() {
    PackageManager pkgMgr = context.getPackageManager();
    List<ResolveInfo> apps;
    Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    apps = pkgMgr.queryIntentActivities(mainIntent, 0);
    Collections.sort(apps, new ResolveInfo.DisplayNameComparator(pkgMgr));
    return apps;
  }

  public String getPkgName(ResolveInfo resolveInfo) {
    return resolveInfo.activityInfo.applicationInfo.packageName;
  }
}