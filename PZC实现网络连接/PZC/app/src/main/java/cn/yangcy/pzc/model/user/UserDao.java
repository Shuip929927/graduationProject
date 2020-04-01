package cn.yangcy.pzc.model.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert()
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    //通过账号查询用户
    @Query("SELECT * FROM tb_user WHERE account = :userAccount")
    LiveData<User> queryUserByAccount(int userAccount);

    //查询所有用户
    @Query("SELECT * FROM tb_user ORDER BY account DESC")
    LiveData<List<User>> queryAllUser();

    //通过活动ID查询报名该活动，状态为成功的User List
    @Query("SELECT * FROM tb_user WHERE account IN " +
            "(SELECT user_id from tb_activities_enroll where activity_enroll_state = 2 AND activity_id = :activityId)")
    LiveData<List<User>> queryActMemberList(int activityId);

    //通过活动ID查询报名该活动，状态为待审核的User List
    @Query("SELECT * FROM tb_user WHERE account IN " +
            "(SELECT user_id from tb_activities_enroll where activity_enroll_state = 1 AND activity_id = :activityId)")
    LiveData<List<User>> queryActEnrollMemberList(int activityId);

    //通过学生组织ID查询报名该组织，状态为成功的User List
    @Query("SELECT * FROM tb_user WHERE account IN " +
            "(SELECT user_id from tb_organization_enroll where organization_enroll_state = 2 AND organization_id = :organizationId)")
    LiveData<List<User>> queryOrgMemberList(int organizationId);

    //通过学生组织ID查询报名该组织，状态为待审核的User List
    @Query("SELECT * FROM tb_user WHERE account IN " +
            "(SELECT user_id from tb_organization_enroll where organization_enroll_state = 1 AND organization_id = :organizationId)")
    LiveData<List<User>> queryOrgEnrollMemberList(int organizationId);

//    @Query("SELECT tb_user.* FROM tb_activities "
//            + "INNER JOIN tb_activities_enroll ON tb_activities.id = tb_activities_enroll.activity_id "
//            + "INNER JOIN tb_user ON tb_activities_enroll.user_id = tb_user.account "
//            + "WHERE tb_activities.id = :activityId AND tb_activities_enroll.activity_enroll_state = 2")
//    public LiveData<List<User>> queryActMemberList(int activityId);
}
