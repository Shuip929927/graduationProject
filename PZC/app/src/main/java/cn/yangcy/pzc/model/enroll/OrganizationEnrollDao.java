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

    @Query("SELECT organization_enroll_state FROM tb_organization_enroll " +
            "WHERE user_id = :userAccount AND organization_id = :organizationID")
    int queryOrganizationEnrollState(int userAccount,int organizationID);

    @Query("SELECT COUNT(*) FROM tb_organization_enroll " +
            "WHERE user_id = :userAccount AND organization_enroll_state = 2")
    int queryUserEnrollOrganizationNumber(int userAccount);

    @Query("SELECT * FROM tb_organization_enroll " +
            "WHERE user_id = :userAccount AND organization_id = :organizationId")
    LiveData<OrganizationEnroll> queryOrganizationEnrollLive(int userAccount, int organizationId);

    @Query("SELECT * FROM tb_organization_enroll " +
            "WHERE user_id = :userAccount AND organization_id = :organizationId")
    OrganizationEnroll queryOrganizationExist(int userAccount, int organizationId);

    @Query("SELECT user_id FROM tb_organization_enroll WHERE organization_id = :organizationId AND organization_enroll_state = 2")
    List<Integer> queryOrganizationMember(int organizationId);

    @Query("SELECT user_id FROM tb_organization_enroll WHERE organization_id = :organizationId AND organization_enroll_state = 1")
    List<Integer> queryOrganizationEnrollMember(int organizationId);

    @Query("SELECT COUNT(*) FROM tb_organization_enroll " +
            "WHERE organization_id = :organizationId AND organization_enroll_state = 2")
    int queryOrganizationMemberNumber(int organizationId);
}
