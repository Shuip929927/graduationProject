package cn.yangcy.pzc.model.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.net.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivitiesRepository {

    private static final String TAG = "ActivitiesRepository";
    private ActivitiesDao activitiesDao;
    private Gson gson = new Gson();

    public ActivitiesRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        activitiesDao = dataBase.getActivitiesDao();
        Log.i(TAG, "数据库连接 ");
    }

    public void addActivities(Activities activities){
        Log.i(TAG, "添加新的活动 ");
        new AddAsync(activitiesDao).execute(activities);
    }

    public void addActivitiesNet(Activities activities){
        Log.i(TAG, "添加新的活动 Net ");
        String json;
        json = gson.toJson(activities);
        System.out.println(json);
        final RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpResponse(Config.API_URL + Config.POST_ADD_ACTIVITIES, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }

    public void deleteActivities(Activities activities){
        Log.i(TAG, "删除活动 ");
        new DeleteAsync(activitiesDao).execute(activities);
    }

    public void updateActivities(Activities activities){
        Log.i(TAG, "修改活动 ");
        new UpdateAsync(activitiesDao).execute(activities);
    }

    public void updateActivitiesNet(Activities activities){
        Log.i(TAG, "修改活动 Net ");
        String json;
        json = gson.toJson(activities);
        System.out.println(json);
        final RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpPUT(Config.API_URL + Config.PUT_UPDATE_ACTIVITIES, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }


    public LiveData<List<Activities>> getAllActivities() {
        Log.i(TAG, "查询所有未删除的活动 ");
        return activitiesDao.queryAllActivities();
    }

    public LiveData<List<Activities>> getAllActivitiesNet() {
        Log.i(TAG, "查询所有未删除的活动Net ");
        final MutableLiveData<List<Activities>> actList = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ALL_ACTIVITIES, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                actList.postValue(null);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String bodyRes = response.body().string();
                    String res = bodyRes.trim();
                    System.out.println(res);
                    String res2 = bodyRes.replaceAll(" ","");
                    System.out.println(res2);
                    Type type = new TypeToken<List<Activities>>(){}.getType();
                    List<Activities> resList = gson.fromJson(res,type);
                    System.out.println(resList.get(0).toString());
                    actList.postValue(resList);
                }
                response.close();
            }
        });
        return actList;
    }

    public LiveData<Activities> getActivitiesById(int activitiesId) {
        Log.i(TAG, "通过ID 查询活动 ");
        return activitiesDao.queryActivityById(activitiesId);
    }

    public LiveData<Activities> getActivitiesByIdNet(int activitiesId) {
        Log.i(TAG, "通过ID 查询活动Net ");
        final MutableLiveData<Activities> act = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ACTIVITIES_BY_ID + activitiesId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String res = response.body().string();
                    Activities resAct = gson.fromJson(res,Activities.class);
                    act.postValue(resAct);
                }
                response.close();
            }
        });
        return act;
    }

    //TODO 多表查询
    public LiveData<List<Activities>> getUserEnrollActivitiesList(int userId){
        Log.i(TAG, "通过User查询用户参加的活动");
        return activitiesDao.queryMemberEnrollActivitiesList(userId);
    }

    public LiveData<List<Activities>> getUserEnrollActivitiesListNet(int userId){
        Log.i(TAG, "通过User查询用户参加的活动 Net");
        final MutableLiveData<List<Activities>> actListLive = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ENROLL_STATE2_ACTIVITIES_LIST_BY_USER_ID + userId,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.code() == 200){
                            String res = response.body().string();
                            Type type = new TypeToken<List<Activities>>(){}.getType();
                            List<Activities> list = gson.fromJson(res,type);
                            actListLive.postValue(list);
                        }
                        response.close();
                    }
                });
        return actListLive;
    }

    //TODO 多表查询
    public LiveData<List<Activities>> getOrganizationHoldActivitiesList(int userId) {
        Log.i(TAG, "通过UserId查询是否为学生组织负责人，从而查询该组织举办的所有活动");
        return activitiesDao.queryOrganizationHoldActivities(userId);
    }

    public LiveData<List<Activities>> getOrganizationHoldActivitiesListNet(int userId) {
        Log.i(TAG, "通过UserId查询是否为学生组织负责人，从而查询该组织举办的所有活动 Net");
        final MutableLiveData<List<Activities>> actListLive = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ORG_HOLD_ACT_BY_USER_ID + userId,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.code() == 200){
                            String res = response.body().string();
                            Type type = new TypeToken<List<Activities>>(){}.getType();
                            List<Activities> list = gson.fromJson(res,type);
                            actListLive.postValue(list);
                        }
                        response.close();
                    }
                });
        return actListLive;
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

