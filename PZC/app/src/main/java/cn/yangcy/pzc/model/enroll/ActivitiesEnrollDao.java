package cn.yangcy.pzc.model.enroll;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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

    @Query("SELECT user_id FROM tb_activities_enroll " +
            "WHERE activity_id = :activitiesId AND activity_enroll_state = 2")
    List<Integer> queryActivitiesMember(int activitiesId);

    @Query("SELECT user_id FROM tb_activities_enroll " +
            "WHERE activity_id = :activitiesId AND activity_enroll_state = 1")
    List<Integer> queryActivitiesEnrollMember(int activitiesId);
}
