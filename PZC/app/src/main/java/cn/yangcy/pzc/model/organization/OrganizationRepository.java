package cn.yangcy.pzc.model.organization;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class OrganizationRepository {

    private OrganizationDao organizationDao;
    private LiveData<List<Organization>> organizationLive;

    public OrganizationRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        organizationDao = dataBase.getOrganizationDao();
        organizationLive = organizationDao.queryAllOrganization();
    }

    public LiveData<List<Organization>> getAllOrganization() {
        return organizationLive;
    }
}
