package cn.yangcy.pzc.model.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class ActivitiesRepository {

    private static final String TAG = "ActivitiesRepository";
    private ActivitiesDao activitiesDao;

    public ActivitiesRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        activitiesDao = dataBase.getActivitiesDao();
        Log.i(TAG, "数据库连接 ");
    }

    public void addActivities(Activities activities){
        Log.i(TAG, "添加新的活动 ");
        new AddAsync(activitiesDao).execute(activities);
    }

    public void deleteActivities(Activities activities){
        Log.i(TAG, "删除活动 ");
        new DeleteAsync(activitiesDao).execute(activities);
    }

    public void updateActivities(Activities activities){
        Log.i(TAG, "修改活动 ");
        new UpdateAsync(activitiesDao).execute(activities);
    }

    public LiveData<List<Activities>> getAllActivities() {
        Log.i(TAG, "查询所有未删除的活动 ");
        return activitiesDao.queryAllActivities();
    }

    public LiveData<Activities> getActivitiesById(int activitiesId) {
        Log.i(TAG, "通过ID 查询活动 ");
        return activitiesDao.queryActivityById(activitiesId);
    }

    public LiveData<List<Activities>> getUserEnrollActivitiesList(List<Integer> list) {
        Log.i(TAG, "通过一系列ID查询用户参加的活动");
        int[] activities = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, "将list数据转为int数组: "+ list.get(i));
            activities[i] = list.get(i);
        }
        return activitiesDao.queryUserEnrollActivities(activities);
    }

    public LiveData<List<Activities>> getOrganizationHoldActivitiesList(int organizationId) {
        Log.i(TAG, "通过组织Id查询组织举办的所有活动");
        return activitiesDao.queryOrganizationHoldActivities(organizationId);
    }

    static class AddAsync extends AsyncTask<Activities,Void,Void> {
        private ActivitiesDao activitiesDao;

        AddAsync(ActivitiesDao activitiesDao) {
            this.activitiesDao = activitiesDao;
        }

        @Override
        protected Void doInBackground(Activities... activities) {
            Log.i(TAG, "doInBackground: AddAsync");
            activitiesDao.insertActivities(activities[0]);
            return null;
        }
    }

    static class DeleteAsync extends AsyncTask<Activities,Void,Void> {
        private ActivitiesDao activitiesDao;

        DeleteAsync(ActivitiesDao activitiesDao) {
            this.activitiesDao = activitiesDao;
        }

        @Override
        protected Void doInBackground(Activities... activities) {
            Log.i(TAG, "doInBackground: DeleteAsync");
            activitiesDao.deleteActivities(activities[0]);
            return null;
        }
    }


    static class UpdateAsync extends AsyncTask<Activities,Void,Void> {
        private ActivitiesDao activitiesDao;

        UpdateAsync(ActivitiesDao activitiesDao) {
            this.activitiesDao = activitiesDao;
        }

        @Override
        protected Void doInBackground(Activities... activities) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            activitiesDao.updateActivities(activities[0]);
            return null;
        }
    }
}

