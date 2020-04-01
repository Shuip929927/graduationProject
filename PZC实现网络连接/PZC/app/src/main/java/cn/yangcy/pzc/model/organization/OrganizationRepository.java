package cn.yangcy.pzc.model.organization;

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

public class OrganizationRepository {

    private static final String TAG = "OrganizationRepository";
    private OrganizationDao organizationDao;
    private Gson gson = new Gson();

    public OrganizationRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        organizationDao = dataBase.getOrganizationDao();
        Log.i(TAG, "数据库连接");
    }
    public void addOrganization(Organization organization){
        Log.i(TAG, "增加学生组织 ");
        new AddAsync(organizationDao).execute(organization);
    }

    public void deleteOrganization(Organization organization){
        Log.i(TAG, "删除学生组织 ");
        new DeleteAsync(organizationDao).execute(organization);
    }

    public void updateOrganization(Organization organization){
        Log.i(TAG, "更新学生组织信息 ");
        new UpdateAsync(organizationDao).execute(organization);
    }

    public void updateOrganizationNet(Organization organization){
        Log.i(TAG, "更新学生组织信息Net ");
        String json;
        json = gson.toJson(organization);
        System.out.println(json);
        final RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpPUT(Config.API_URL + Config.PUT_UPDATE_ORGANIZATION, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }

    public LiveData<List<Organization>> getAllOrganization(){
        Log.i(TAG, "查询所有学生组织 ");
        return organizationDao.queryAllOrganization();
    }

    public LiveData<List<Organization>> getAllOrganizationNet(){
        Log.i(TAG, "查询所有学生组织Net ");
        final MutableLiveData<List<Organization>> orgList = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ALL_ORGANIZATION_LIST, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String res = response.body().string();
                    Type type = new TypeToken<List<Organization>>(){}.getType();
                    List<Organization> resList = gson.fromJson(res,type);
                    orgList.postValue(resList);
                }
                response.close();
            }
        });
        return orgList;
    }

    public LiveData<Organization> queryOrganizationById(int organizationId){
        Log.i(TAG, "通过ID查询学生组织 ");
        return organizationDao.queryOrganizationById(organizationId);
    }

    public LiveData<Organization> queryOrganizationByIdNet(int organizationId){
        Log.i(TAG, "通过ID查询学生组织 Net");
        final MutableLiveData<Organization> organization = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ORGANIZATION_BY_ID + organizationId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String res = response.body().string();
                    Organization resOrganization = gson.fromJson(res,Organization.class);
                    organization.postValue(resOrganization);
                }
                response.close();
            }
        });
        return organization;
    }


    public LiveData<List<Organization>> queryUserEnrollOrganizationList(int userAccount){
        Log.i(TAG, "查询某个用户报名的所有学生组织ID ");
        return organizationDao.queryUserEnrollOrganizationList(userAccount);
    }

    public LiveData<List<Organization>> queryUserEnrollOrganizationListNet(int userAccount){
        Log.i(TAG, "查询某个用户报名的所有学生组织ID Net");
        final MutableLiveData<List<Organization>> orgList = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_USER_ENROLL_STATE2_ORG_LIST_BY_USER_ID
                + userAccount, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.code() == 200){
                            String res = response.body().string();
                            Type type = new TypeToken<List<Organization>>(){}.getType();
                            List<Organization> resOrgList = gson.fromJson(res,type);
                            orgList.postValue(resOrgList);
                        }
                        response.close();
                    }
                });
        return orgList;
    }

    static class AddAsync extends AsyncTask<Organization,Void,Void> {
        private OrganizationDao organizationDao;

        AddAsync(OrganizationDao organizationDao) {
            this.organizationDao = organizationDao;
        }
        @Override
        protected Void doInBackground(Organization... organizations) {
            Log.i(TAG, "doInBackground: AddAsync");
            organizationDao.insertOrganization(organizations[0]);
            return null;
        }
    }
    static class DeleteAsync extends AsyncTask<Organization,Void,Void> {
        private OrganizationDao organizationDao;

        DeleteAsync(OrganizationDao organizationDao) {
            this.organizationDao = organizationDao;
        }
        @Override
        protected Void doInBackground(Organization... organizations) {
            Log.i(TAG, "doInBackground: DeleteAsync");
            organizationDao.deleteOrganization(organizations[0]);
            return null;
        }
    }

    static class UpdateAsync extends AsyncTask<Organization,Void,Void> {
        private OrganizationDao organizationDao;

        UpdateAsync(OrganizationDao organizationDao) {
            this.organizationDao = organizationDao;
        }
        @Override
        protected Void doInBackground(Organization... organizations) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            organizationDao.updateOrganization(organizations[0]);
            return null;
        }
    }
}
