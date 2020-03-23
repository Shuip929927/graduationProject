package cn.yangcy.pzc.model.enroll;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.yangcy.pzc.model.organization.Organization;

@Dao
public interface OrganizationEnrollDao {

    @Insert
    void insertOrganizationEnrollMessage(OrganizationEnroll organizationEnroll);

    @Delete
    void deleteOrganizationEnrollMessage(OrganizationEnroll organizationEnroll);

    @Update
    void updateOrganizationEnrollMessage(OrganizationEnroll organizationEnroll);

    //查询一个用户报名的一个组织的信息
    @Query("SELECT * FROM tb_organization_enroll " +
            "WHERE user_id = :userAccount AND organization_id = :organizationId")
    LiveData<OrganizationEnroll> queryOrganizationEnrollLive(int userAccount, int organizationId);

    //查询一个组织的所有报名状态为2的用户ID
    @Query("SELECT * FROM tb_organization_enroll " +
            "WHERE organization_id = :organizationId AND organization_enroll_state = 2")
    LiveData<List<OrganizationEnroll>> queryOrganizationMember(int organizationId);

    //查询一个组织的所有报名状态为1的用户ID
    @Query("SELECT * FROM tb_organization_enroll " +
            "WHERE organization_id = :organizationId AND organization_enroll_state = 1")
    LiveData<List<OrganizationEnroll>> queryOrganizationEnrollMember(int organizationId);

    //查询某个用户报名的所有学生组织
    @Query("SELECT * FROM tb_organization_enroll " +
            "WHERE user_id = :userId AND organization_enroll_state = 2")
    LiveData<List<OrganizationEnroll>> queryUserEnrollOrganizationList(int userId);
}
