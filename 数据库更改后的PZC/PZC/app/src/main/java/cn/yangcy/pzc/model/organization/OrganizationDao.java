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

    @Query("SELECT * FROM tb_organization ORDER BY organization_name ASC")
    LiveData<List<Organization>> queryAllOrganization();

    @Query("SELECT * FROM tb_organization WHERE id = :organizationId")
    LiveData<Organization> queryOrganizationDetailById(int organizationId);

    @Query("SELECT * FROM tb_organization WHERE id = :organizationId")
    Organization queryOrganizationById(int organizationId);

    @Query("SELECT id FROM tb_organization WHERE organization_name = :organizationName")
    int queryOrganizationIdByName(String organizationName);

    @Query("SELECT id FROM tb_organization WHERE person_id = :personInChargeId")
    int queryPersonInChargeOrgId(int personInChargeId);


    @Query("SELECT * FROM tb_organization WHERE id in(:organizations) ORDER BY id DESC")
    LiveData<List<Organization>> queryUserEnrollOrganization(int[] organizations);

}
