package cn.yangcy.pzc.model.enroll;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import cn.yangcy.pzc.model.activities.Activities;

@Dao
public interface ActivitiesEnrollDao {

    @Insert
    void insertEnrollMessage(ActivitiesEnroll activitiesEnroll);

    @Delete
    void deleteEnrollMessage(ActivitiesEnroll activitiesEnroll);

    @Update
    void updateEnrollMessage(ActivitiesEnroll activitiesEnroll);

    @Query("SELECT * FROM tb_activities_enroll " +
            "WHERE user_id = :userAccount AND activity_id = :activitiesId")
    LiveData<ActivitiesEnroll> queryActivitiesEnrollLive(int userAccount, int activitiesId);

    @Query("SELECT * FROM tb_activities_enroll " +
            "WHERE user_id = :userAccount AND activity_id = :activitiesId")
    ActivitiesEnroll queryActivitiesExist(int userAccount, int activitiesId);
}
