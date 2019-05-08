package io.github.ovso.onea.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.github.ovso.onea.data.db.model.AppEntity;
import java.util.List;

@Dao public interface Apps {

  @Query("SELECT * FROM APPS_INFO")
  LiveData<List<AppEntity>> getLiveItems();

  @Query("SELECT * FROM APPS_INFO")
  LiveData<AppEntity> getLiveItem();

  @Query("SELECT * FROM APPS_INFO")
  List<AppEntity> getItems();

  @Query("DELETE FROM APPS_INFO")
  void deleteAll();

  @Insert
  void insert(AppEntity... entities);

  @Insert
  void insert(List<AppEntity> entities);

  @Delete
  void delete(AppEntity entity);

  @Update
  void update(AppEntity entity);

  @Query("SELECT * FROM APPS_INFO WHERE MARKET LIKE :name")
  List<AppEntity> getEntityByMarket(String name);

  @Query("SELECT SUM(APK_SIZE) FROM APPS_INFO WHERE MARKET LIKE :name")
  long getApkSizeSumByMarket(String name);
}