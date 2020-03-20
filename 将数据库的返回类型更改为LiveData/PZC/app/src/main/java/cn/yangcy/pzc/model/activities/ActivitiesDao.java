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

    //查询所有未删除的活动
    @Query("SELECT * FROM tb_activities WHERE isDelete = 0 ORDER BY activity_create_time DESC")
    LiveData<List<Activities>> queryAllActivities();

    //通过ID 查询活动
    @Query("SELECT * FROM tb_activities WHERE id = :activitiesId")
    LiveData<Activities> queryActivityById(int activitiesId);

    //通过一系列ID查询用户参加的活动
    @Query("SELECT * FROM tb_activities WHERE isDelete = 0 AND id  in (:activitiesId) ORDER BY activity_create_time DESC")
    LiveData<List<Activities>> queryUserEnrollActivities(int[] activitiesId);

    //通过组织Id 查询 组织举办的所有活动
    @Query("SELECT * FROM tb_activities WHERE organization_id = :organizationId ORDER BY activity_create_time DESC")
    LiveData<List<Activities>> queryOrganizationHoldActivities(int organizationId);
}
