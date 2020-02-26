package cn.yangcy.pzc.model.activities;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class ActivitiesRepository {

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
}

