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

    @Query("SELECT * FROM tb_activities WHERE isDelete = 0 AND id IN " +
            "(SELECT activity_id from tb_activities_enroll where activity_enroll_state = 2 AND user_id = :userId)")
    LiveData<List<Activities>> queryMemberEnrollActivitiesList(int userId);

    //通过组织Id 查询 组织举办的所有活动
    @Query("SELECT * FROM tb_activities " +
            "WHERE organization_id = (SELECT id FROM tb_organization WHERE person_id = :userId) " +
            "ORDER BY activity_create_time DESC")
    LiveData<List<Activities>> queryOrganizationHoldActivities(int userId);


}
