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

    //通过UserAccount和活动Id 查询
    @Query("SELECT * FROM tb_activities_enroll " +
            "WHERE user_id = :userAccount AND activity_id = :activitiesId")
    LiveData<ActivitiesEnroll> queryActivitiesEnrollLive(int userAccount, int activitiesId);

    //通过活动id查询报名活动成功的用户
    @Query("SELECT * FROM tb_activities_enroll " +
            "WHERE activity_id = :activitiesId AND activity_enroll_state = 2")
    LiveData<List<ActivitiesEnroll>> queryActivitiesMember(int activitiesId);

    //通过活动id查询报名活动待审核的用户
    @Query("SELECT * FROM tb_activities_enroll " +
            "WHERE activity_id = :activitiesId AND activity_enroll_state = 1")
    LiveData<List<ActivitiesEnroll>> queryActivitiesEnrollMember(int activitiesId);

    //通过用户账号查询 用户报名成功的活动
    @Query("SELECT * FROM tb_activities_enroll " +
            "WHERE user_id = :userId AND activity_enroll_state = 2")
    LiveData<List<ActivitiesEnroll>> queryUserEnrollActivities(int userId);


}
