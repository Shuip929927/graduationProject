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
    LiveData<Organization> queryOrganizationDetail(int organizationId);
}
