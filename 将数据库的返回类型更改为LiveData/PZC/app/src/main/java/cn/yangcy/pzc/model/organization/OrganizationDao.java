package cn.yangcy.pzc.model.organization;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.yangcy.pzc.model.activities.Activities;

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

    //通过组织名查询组织
    @Query("SELECT * FROM tb_organization WHERE organization_name = :organizationName")
    LiveData<Organization> queryOrganizationIdByName(String organizationName);

    //通过负责人账号 查询组织
    @Query("SELECT * FROM tb_organization WHERE person_id = :personInChargeId")
    LiveData<Organization> queryPersonInChargeOrgId(int personInChargeId);

    //通过一系列ID 查询 用户所参加的组织
    @Query("SELECT * FROM tb_organization WHERE id in(:organizations) ORDER BY id DESC")
    LiveData<List<Organization>> queryUserEnrollOrganization(int[] organizations);

}
