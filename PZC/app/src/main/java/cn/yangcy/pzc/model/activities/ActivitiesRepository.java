package cn.yangcy.pzc.model.activities;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.DataBase;

public class ActivitiesRepository {

    private ActivitiesDao activitiesDao;
    private LiveData<List<Activities>> activitiesLive;

    public ActivitiesRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        activitiesDao = dataBase.getActivitiesDao();
        activitiesLive = activitiesDao.queryAllActivities();
    }

    public LiveData<List<Activities>> getAllActivities() {
        return activitiesLive;
    }
}

