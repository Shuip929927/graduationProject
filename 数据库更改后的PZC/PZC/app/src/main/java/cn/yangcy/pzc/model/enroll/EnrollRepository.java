package cn.yangcy.pzc.model.enroll;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class EnrollRepository {

    private static final String TAG = "EnrollRepository";
    private OrganizationEnrollDao organizationEnrollDao;
    private ActivitiesEnrollDao activitiesEnrollDao;

    public EnrollRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        organizationEnrollDao = dataBase.getOrganizationEnrollDao();
        activitiesEnrollDao = dataBase.getActivitiesEnrollDao();
    }

    public LiveData<ActivitiesEnroll> queryActivitiesEnrollState(int userAccount, int activitiesId){
        ActivitiesEnroll activitiesEnroll = activitiesEnrollDao.queryActivitiesExist(userAccount,activitiesId);
        LiveData<ActivitiesEnroll> activitiesEnrollLiveData;
        if(activitiesEnroll == null){
            ActivitiesEnroll ae = new ActivitiesEnroll(userAccount,activitiesId);
                activitiesEnrollDao.insertEnrollMessage(ae);
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
        LiveData<OrganizationEnroll> organizationEnrollLiveData;
        if(organizationEnroll == null){
            OrganizationEnroll oe = new OrganizationEnroll(userAccount,organizationId);
            organizationEnrollDao.insertOrganizationEnrollMessage(oe);
            organizationEnrollLiveData = organizationEnrollDao.queryOrganizationEnrollLive(userAccount,organizationId);
            Log.i(TAG, "doInBackground: OE insert"+userAccount + oe);
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

        DoActivitiesEnrollAsyncTask(ActivitiesEnrollDao activitiesEnrollDao) {
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

        DoOrganizationEnrollAsyncTask(OrganizationEnrollDao organizationEnrollDao) {
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
        return organizationEnrollDao.queryUserEnrollOrganizationNumber(userAccount);
    }

    public List<Integer> getOrganizationMemberList(int organizationId) {
        Log.i(TAG, "getOrganizationMemberList");
        return organizationEnrollDao.queryOrganizationMember(organizationId);
    }

    public List<Integer> getActivitiesMemberList(int activitiesId) {
        Log.i(TAG, "getActivitiesMemberList");
        return activitiesEnrollDao.queryActivitiesMember(activitiesId);
    }

    public List<Integer> getOrganizationMemberEnrollList(int organizationId) {
        Log.i(TAG, "getMemberEnrollList");
        return organizationEnrollDao.queryOrganizationEnrollMember(organizationId);
    }

    public List<Integer> getActivitiesMemberEnrollList(int activitiesId) {
        Log.i(TAG, "getActivitiesMemberEnrollList");
        return activitiesEnrollDao.queryActivitiesEnrollMember(activitiesId);
    }

    public List<Integer> getUserEnrollOrganizationList(int userId) {
        Log.i(TAG, "getUserEnrollOrganizationList");
        return organizationEnrollDao.queryUserEnrollOrganizationList(userId);
    }

    public List<Integer> getUserEnrollActivitiesList(int userId) {
        Log.i(TAG, "getUserEnrollActivitiesList");
        return activitiesEnrollDao.queryUserEnrollActivities(userId);
    }

    public int getOrganizationMemberNum(int organizationId) {
        Log.i(TAG, "getOrganizationMemberNum");
        return organizationEnrollDao.queryOrganizationMemberNumber(organizationId);
    }

    public void updateOrganizationMemberEnroll(int userAccount, int organizationId, int state){
        Log.i(TAG, "updateOrganizationMemberEnroll");
        OrganizationEnroll oe = organizationEnrollDao.queryOrganizationExist(userAccount,organizationId);
        oe.setState(state);
        organizationEnrollDao.updateOrganizationEnrollMessage(oe);
    }

    public void deleteOrganizationMemberEnroll(int userAccount, int organizationId){
        Log.i(TAG, "deleteOrganizationMemberEnroll");
        OrganizationEnroll oe = organizationEnrollDao.queryOrganizationExist(userAccount,organizationId);
        organizationEnrollDao.deleteOrganizationEnrollMessage(oe);
    }

    public void updateActivitiesMemberEnroll(int userAccount, int activitiesId, int state){
        Log.i(TAG, "updateOrganizationMemberEnroll");
        ActivitiesEnroll ae = activitiesEnrollDao.queryActivitiesExist(userAccount,activitiesId);
        ae.setState(state);
        activitiesEnrollDao.updateEnrollMessage(ae);
    }

    public void deleteActivitiesMemberEnroll(int userAccount, int activitiesId){
        Log.i(TAG, "deleteOrganizationMemberEnroll");
        ActivitiesEnroll ae = activitiesEnrollDao.queryActivitiesExist(userAccount,activitiesId);
        activitiesEnrollDao.deleteEnrollMessage(ae);
    }
}
