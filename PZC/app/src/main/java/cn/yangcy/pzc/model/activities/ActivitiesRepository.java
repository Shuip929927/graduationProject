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
    private LiveData<List<Activities>> activitiesLive;
    private LiveData<Activities> activitiesDetailLive;

    public ActivitiesRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        activitiesDao = dataBase.getActivitiesDao();
        activitiesLive = activitiesDao.queryAllActivities();
    }

    public LiveData<List<Activities>> getAllActivities() {
        return activitiesLive;
    }

    public LiveData<Activities> getActivitiesDetailLive(int activitiesId) {
        activitiesDetailLive = activitiesDao.queryActivitiesDetail(activitiesId);
        return activitiesDetailLive;
    }

    public void updateActivities(Activities activities){
        new UpdateActivitiesAsyncTask(activitiesDao).execute(activities);
    }

    static class UpdateActivitiesAsyncTask extends AsyncTask<Activities,Void,Void>{

        private ActivitiesDao activitiesDao;

        public UpdateActivitiesAsyncTask(ActivitiesDao activitiesDao) {
            this.activitiesDao = activitiesDao;
        }

        @Override
        protected Void doInBackground(Activities... activities) {
            activitiesDao.updateActivities(activities[0]);
            return null;
        }
    }

    public LiveData<List<Activities>> getUserEnrollActivitiesLiveData(List<Integer> list) {
        Log.i(TAG, "getUserEnrollActivitiesLiveData");
        int[] activities = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, "getUserLiveData: "+ list.get(i));
            activities[i] = list.get(i);
        }
        return activitiesDao.queryUserEnrollActivities(activities);
    }

    public LiveData<List<Activities>> getUserHoldActivitiesLiveData(int userChargeOrganizationId) {
        Log.i(TAG, "getUserHoldActivitiesLiveData");
        return activitiesDao.queryUserHoldActivities(userChargeOrganizationId);
    }
}

