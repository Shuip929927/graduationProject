package cn.yangcy.pzc.model.organization;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class OrganizationRepository {

    private static final String TAG = "OrganizationRepository";
    private OrganizationDao organizationDao;
    private LiveData<List<Organization>> organizationLive;
    private LiveData<Organization> organizationDetailLive;

    public OrganizationRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        organizationDao = dataBase.getOrganizationDao();
        organizationLive = organizationDao.queryAllOrganization();

    }

    public LiveData<List<Organization>> getAllOrganization() {
        return organizationLive;
    }

    public LiveData<Organization> getOrganizationDetailLive(int organizationId) {
        organizationDetailLive = organizationDao.queryOrganizationDetail(organizationId);
        return organizationDetailLive;
    }

    public void updateOrganization(Organization organization){
        new UpdateOrganizationAsyncTask(organizationDao).execute(organization);
    }

    static class UpdateOrganizationAsyncTask extends AsyncTask<Organization,Void,Void> {

        private OrganizationDao organizationDao;

        public UpdateOrganizationAsyncTask(OrganizationDao organizationDao) {
            this.organizationDao = organizationDao;
        }

        @Override
        protected Void doInBackground(Organization... organizations) {
            organizationDao.updateOrganization(organizations[0]);
            return null;
        }
    }

    public void updateOrganizationPeopleNumber(int organizationId){
        Log.i(TAG, "updateOrganizationPeopleNumber");
        Organization organization = organizationDao.queryOrganization(organizationId);
        organization.setNumberOfPeople(organization.getNumberOfPeople()+1);
        organizationDao.updateOrganization(organization);
    }
}
