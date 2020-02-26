package cn.yangcy.pzc.model.activities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ActivitiesDao {

    @Insert
    void insertActivities(Activities activities);

    @Delete
    void deleteActivities(Activities activities);

    @Update
    void updateActivities(Activities activities);

    @Query("SELECT * FROM tb_activities ORDER BY activity_create_time DESC")
    LiveData<List<Activities>> queryAllActivities();

    @Query("SELECT * FROM tb_activities WHERE id = :activitiesId")
    LiveData<Activities> queryActivitiesDetail(int activitiesId);
}
