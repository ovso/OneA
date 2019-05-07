package io.github.ovso.onea.data.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.github.ovso.onea.data.db.dao.Apps;
import io.github.ovso.onea.data.db.model.AppsEntity;

@Database(entities = {
    AppsEntity.class,
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
  private final static String DATABASE_NAME = "apps";
  private static AppDatabase instance;

  public static synchronized AppDatabase getInstance(Context context) {
    if (instance == null) {
      instance = buildDatabase(context);
    }
    return instance;
  }

  private static AppDatabase buildDatabase(final Context context) {
    return Room.databaseBuilder(context,
        AppDatabase.class,
        DATABASE_NAME)
        .allowMainThreadQueries()
        .build();
  }

  public static void destroy() {
    if (instance.isOpen()) {
      instance.close();
      instance = null;
    }
  }

  public abstract Apps appsDao();
}