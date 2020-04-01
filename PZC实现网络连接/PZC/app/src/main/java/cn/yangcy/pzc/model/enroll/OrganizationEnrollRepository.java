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
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrganizationEnrollRepository {
    private static final String TAG = "OERepository";
    private OrganizationEnrollDao organizationEnrollDao;
    private Gson gson = new Gson();

    public OrganizationEnrollRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        organizationEnrollDao = dataBase.getOrganizationEnrollDao();
        Log.i(TAG, "数据库连接 ");
    }

    public void addOE(OrganizationEnroll organizationEnroll){
        Log.i(TAG, "添加学生组织报名信息 ");
        new AddAsync(organizationEnrollDao).execute(organizationEnroll);
    }

    public void addOENet(OrganizationEnroll organizationEnroll) {
        Log.i(TAG, "添加学生组织报名信息Net ");
        String json = gson.toJson(organizationEnroll);
        RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpResponse(Config.API_URL + Config.POST_ADD_OE, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }

    public void deleteOE(OrganizationEnroll organizationEnroll){
        Log.i(TAG, "删除学生组织报名信息 ");
        new DeleteAsync(organizationEnrollDao).execute(organizationEnroll);
    }

    public void updateOE(OrganizationEnroll organizationEnroll){
        Log.i(TAG, "更新学生组织报名信息 ");
        new UpdateAsync(organizationEnrollDao).execute(organizationEnroll);
    }

    public void updateOENet(OrganizationEnroll organizationEnroll) {
        Log.i(TAG, "修改学生活动报名信息Net ");
        String json = gson.toJson(organizationEnroll);
        RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpPUT(Config.API_URL + Config.PUT_UPDATE_OE, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }

    public LiveData<OrganizationEnroll> queryOrganizationEnrollLive(int userAccount, int organizationId){
        Log.i(TAG, "查询一个用户报名的一个组织的信息 ");
        return organizationEnrollDao.queryOrganizationEnrollLive(userAccount,organizationId);
    }

    public LiveData<OrganizationEnroll> queryOrganizationEnrollLiveNet(int userAccount, int organizationId) {
        Log.i(TAG, "通过UserAccount和活动Id获取活动报名信息 Net");
        final MutableLiveData<OrganizationEnroll> oeLive = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_OE_BY_USER_ID_AND_ORG_ID
                + userAccount + "&" + organizationId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String res = response.body().string();
                    OrganizationEnroll oe = gson.fromJson(res, OrganizationEnroll.class);
                    oeLive.postValue(oe);
                }
                response.close();
            }
        });
        return oeLive;
    }


    static class AddAsync extends AsyncTask<OrganizationEnroll,Void,Void> {
        private OrganizationEnrollDao organizationEnrollDao;

        AddAsync(OrganizationEnrollDao organizationEnrollDao) {
            this.organizationEnrollDao = organizationEnrollDao;
        }

        @Override
        protected Void doInBackground(OrganizationEnroll... organizationEnrolls) {
            Log.i(TAG, "doInBackground: AddAsync");
            organizationEnrollDao.insertOrganizationEnrollMessage(organizationEnrolls[0]);
            return null;
        }
    }

    static class DeleteAsync extends AsyncTask<OrganizationEnroll,Void,Void> {
        private OrganizationEnrollDao organizationEnrollDao;

        DeleteAsync(OrganizationEnrollDao organizationEnrollDao) {
            this.organizationEnrollDao = organizationEnrollDao;
        }
        @Override
        protected Void doInBackground(OrganizationEnroll... organizationEnrolls) {
            Log.i(TAG, "doInBackground: DeleteAsync");
            organizationEnrollDao.deleteOrganizationEnrollMessage(organizationEnrolls[0]);
            return null;
        }
    }

    static class UpdateAsync extends AsyncTask<OrganizationEnroll,Void,Void> {
        private OrganizationEnrollDao organizationEnrollDao;

        UpdateAsync(OrganizationEnrollDao organizationEnrollDao) {
            this.organizationEnrollDao = organizationEnrollDao;
        }
        @Override
        protected Void doInBackground(OrganizationEnroll... organizationEnrolls) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            organizationEnrollDao.updateOrganizationEnrollMessage(organizationEnrolls[0]);
            return null;
        }
    }
}
