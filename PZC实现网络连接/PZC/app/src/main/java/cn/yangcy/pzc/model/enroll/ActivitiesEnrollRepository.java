package cn.yangcy.pzc.model.enroll;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.google.gson.Gson;

import java.io.IOException;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.net.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivitiesEnrollRepository {

    private static final String TAG = "AERepository";
    private ActivitiesEnrollDao activitiesEnrollDao;
    private Gson gson = new Gson();

    public ActivitiesEnrollRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        activitiesEnrollDao = dataBase.getActivitiesEnrollDao();
        Log.i(TAG, "数据库连接 ");
    }

    //增
    public void addActivitiesEnroll(ActivitiesEnroll activitiesEnroll) {
        Log.i(TAG, "添加学生活动报名信息 ");
        new AddAsync(activitiesEnrollDao).execute(activitiesEnroll);
    }

    public void addActivitiesEnrollNet(ActivitiesEnroll activitiesEnroll) {
        Log.i(TAG, "添加学生活动报名信息Net ");
        String json = gson.toJson(activitiesEnroll);
        RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpResponse(Config.API_URL + Config.POST_ADD_AE, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }

    //删
    public void deleteActivitiesEnroll(ActivitiesEnroll activitiesEnroll) {
        Log.i(TAG, "删除学生活动报名信息 ");
        new DeleteAsync(activitiesEnrollDao).execute(activitiesEnroll);
    }

    //改
    public void updateActivitiesEnroll(ActivitiesEnroll activitiesEnroll) {
        Log.i(TAG, "修改学生活动报名信息 ");
        new UpdateAsync(activitiesEnrollDao).execute(activitiesEnroll);
    }

    public void updateActivitiesEnrollNet(ActivitiesEnroll activitiesEnroll) {
        Log.i(TAG, "修改学生活动报名信息Net ");
        String json = gson.toJson(activitiesEnroll);
        RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpPUT(Config.API_URL + Config.PUT_UPDATE_AE, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }


    public LiveData<ActivitiesEnroll> getActivitiesEnrollLive(int userAccount, int activitiesId) {
        Log.i(TAG, "通过UserAccount和活动Id获取活动报名信息 ");
        return activitiesEnrollDao.queryActivitiesEnrollLive(userAccount, activitiesId);
    }

    public LiveData<ActivitiesEnroll> getActivitiesEnrollLiveNet(int userAccount, int activitiesId) {
        Log.i(TAG, "通过UserAccount和活动Id获取活动报名信息 Net");
        final MutableLiveData<ActivitiesEnroll> aeLive = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_AE_BY_USER_ID_AND_ACT_ID
                + userAccount + "&" + activitiesId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String res = response.body().string();
                    ActivitiesEnroll ae = gson.fromJson(res, ActivitiesEnroll.class);
                    aeLive.postValue(ae);
                }
                response.close();
            }
        });
        return aeLive;
    }

    static class AddAsync extends AsyncTask<ActivitiesEnroll, Void, Void> {
        private ActivitiesEnrollDao activitiesEnrollDao;

        AddAsync(ActivitiesEnrollDao activitiesEnrollDao) {
            this.activitiesEnrollDao = activitiesEnrollDao;
        }

        @Override
        protected Void doInBackground(ActivitiesEnroll... activitiesEnrolls) {
            Log.i(TAG, "doInBackground: AddAsync");
            activitiesEnrollDao.insertEnrollMessage(activitiesEnrolls[0]);
            return null;
        }
    }

    static class DeleteAsync extends AsyncTask<ActivitiesEnroll, Void, Void> {
        private ActivitiesEnrollDao activitiesEnrollDao;

        DeleteAsync(ActivitiesEnrollDao activitiesEnrollDao) {
            this.activitiesEnrollDao = activitiesEnrollDao;
        }

        @Override
        protected Void doInBackground(ActivitiesEnroll... activitiesEnrolls) {
            Log.i(TAG, "doInBackground: DeleteAsync");
            activitiesEnrollDao.deleteEnrollMessage(activitiesEnrolls[0]);
            return null;
        }
    }

    static class UpdateAsync extends AsyncTask<ActivitiesEnroll, Void, Void> {
        private ActivitiesEnrollDao activitiesEnrollDao;

        UpdateAsync(ActivitiesEnrollDao activitiesEnrollDao) {
            this.activitiesEnrollDao = activitiesEnrollDao;
        }

        @Override
        protected Void doInBackground(ActivitiesEnroll... activitiesEnrolls) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            activitiesEnrollDao.updateEnrollMessage(activitiesEnrolls[0]);
            return null;
        }
    }
}
