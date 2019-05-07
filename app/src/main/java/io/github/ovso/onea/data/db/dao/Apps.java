package io.github.ovso.onea.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.github.ovso.onea.data.db.model.AppsEntity;
import java.util.List;

@Dao public interface Apps {

  @Query("SELECT * FROM APPS_INFO")
  LiveData<List<AppsEntity>> getLiveItems();

  @Query("SELECT * FROM APPS_INFO")
  LiveData<AppsEntity> getLiveItem();

  @Query("SELECT * FROM APPS_INFO")
  List<AppsEntity> getItems();

  @Insert
  void insert(AppsEntity... entities);

  @Insert
  void insert(List<AppsEntity> entities);

  @Delete
  void delete(AppsEntity entity);

  @Update
  void update(AppsEntity entity);
}