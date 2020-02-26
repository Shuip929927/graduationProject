package cn.yangcy.pzc.model.enroll;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.model.organization.Organization;

public class EnrollRepository {

    private static final String TAG = "EnrollRepository";
    private OrganizationEnrollDao organizationEnrollDao;
    private ActivitiesEnrollDao activitiesEnrollDao;
    private LiveData<ActivitiesEnroll> activitiesEnrollLiveData;
    private LiveData<OrganizationEnroll> organizationEnrollLiveData;
    private int UserEnrollOrganizationNum;

    public EnrollRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        organizationEnrollDao = dataBase.getOrganizationEnrollDao();
        activitiesEnrollDao = dataBase.getActivitiesEnrollDao();
    }

    public LiveData<ActivitiesEnroll> queryActivitiesEnrollState(int userAccount, int activitiesId){
        ActivitiesEnroll activitiesEnroll = activitiesEnrollDao.queryActivitiesExist(userAccount,activitiesId);
        if(activitiesEnroll == null){
            activitiesEnroll = new ActivitiesEnroll(userAccount,activitiesId);
                activitiesEnrollDao.insertEnrollMessage(activitiesEnroll);
                activitiesEnrollLiveData = activitiesEnrollDao.queryActivitiesEnrollLive(userAccount,activitiesId);
            Log.i(TAG, "doInBackground: AE insert"+userAccount + activitiesId);
        } else{
            Log.i(TAG, "queryActivitiesEnrollState: AE NOT NULL");
            activitiesEnrollLiveData = activitiesEnrollDao.queryActivitiesEnrollLive(userAccount,activitiesId);
        }
        return activitiesEnrollLiveData;
    }

    public LiveData<OrganizationEnroll> queryOrganizationEnrollState(int userAccount, int organizationId){
        OrganizationEnroll organizationEnroll = organizationEnrollDao.queryOrganizationExist(userAccount,organizationId);
        if(organizationEnroll == null){
            organizationEnroll = new OrganizationEnroll(userAccount,organizationId);
            organizationEnrollDao.insertOrganizationEnrollMessage(organizationEnroll);
            organizationEnrollLiveData = organizationEnrollDao.queryOrganizationEnrollLive(userAccount,organizationId);
            Log.i(TAG, "doInBackground: OE insert"+userAccount + organizationEnroll);
        } else{
            Log.i(TAG, "queryOrganizationEnrollState: OE NOT NULL");
            organizationEnrollLiveData = organizationEnrollDao.queryOrganizationEnrollLive(userAccount,organizationId);
        }
        return organizationEnrollLiveData;
    }

    public void activitiesDoEnroll(ActivitiesEnroll activitiesEnroll){
        Log.i(TAG, "activitiesDoEnroll: ");
        new DoActivitiesEnrollAsyncTask(activitiesEnrollDao).execute(activitiesEnroll);
    }


    static class DoActivitiesEnrollAsyncTask extends AsyncTask<ActivitiesEnroll,Void,Void>{

        private ActivitiesEnrollDao activitiesEnrollDao;

        public DoActivitiesEnrollAsyncTask(ActivitiesEnrollDao activitiesEnrollDao) {
            this.activitiesEnrollDao = activitiesEnrollDao;
        }

        @Override
        protected Void doInBackground(ActivitiesEnroll... activitiesEnrolls) {
                Log.i(TAG, "doInBackground: AE update");
                activitiesEnrollDao.updateEnrollMessage(activitiesEnrolls[0]);
            return null;
        }
    }

    public void organizationDoEnroll(OrganizationEnroll organizationEnroll){
        Log.i(TAG, "organizationDoEnroll: " );
        new DoOrganizationEnrollAsyncTask(organizationEnrollDao).execute(organizationEnroll);
    }

    static class DoOrganizationEnrollAsyncTask extends AsyncTask<OrganizationEnroll,Void,Void>{

        private OrganizationEnrollDao organizationEnrollDao;

        public DoOrganizationEnrollAsyncTask(OrganizationEnrollDao organizationEnrollDao) {
            this.organizationEnrollDao = organizationEnrollDao;
        }

        @Override
        protected Void doInBackground(OrganizationEnroll... organizationEnrolls) {
            Log.i(TAG, "doInBackground: OE update");
            organizationEnrollDao.updateOrganizationEnrollMessage(organizationEnrolls[0]);
            return null;
        }
    }

    public int getUserEnrollOrganizationNum(int userAccount) {
        UserEnrollOrganizationNum = organizationEnrollDao.queryUserEnrollOrganizationNumber(userAccount);
        return UserEnrollOrganizationNum;
    }
}
