package io.github.ovso.onea.data.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.ToString;

@ToString @Entity(tableName = "APPS_INFO") public class AppEntity {
  @PrimaryKey(autoGenerate = true)
  public int id;

  @ColumnInfo(name = "PKG")
  public String pkg;
  @ColumnInfo(name = "MARKET")
  public String market;
  @ColumnInfo(name = "VERSION")
  public String version;
  @ColumnInfo(name = "LAST_UPDATE_TIME")
  public long lastUpdateTime;
  @ColumnInfo(name = "FIRST_INSTALL_TIME")
  public long firstInstallTime;
  @ColumnInfo(name = "APK_PATH")
  public String apkPath;
  @ColumnInfo(name = "APK_SIZE")
  public long apkSize;
}