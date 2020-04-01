package cn.yangcy.pzc.model.organization;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;

@Dao
public interface OrganizationDao {

    @Insert
    void insertOrganization(Organization organization);

    @Delete
    void deleteOrganization(Organization organization);

    @Update
    void updateOrganization(Organization organization);

    //查询所有组织
    @Query("SELECT * FROM tb_organization ORDER BY organization_name ASC")
    LiveData<List<Organization>> queryAllOrganization();

    //通过ID 查询组织
    @Query("SELECT * FROM tb_organization WHERE id = :organizationId")
    LiveData<Organization> queryOrganizationById(int organizationId);

    //查询某个用户报名的所有学生组织
    @Query("SELECT * FROM tb_organization WHERE person_id = :userId OR id IN " +
            "(SELECT organization_id from tb_organization_enroll where organization_enroll_state = 2 " +
            "AND user_id = :userId)")
    public LiveData<List<Organization>> queryUserEnrollOrganizationList(int userId);

}
