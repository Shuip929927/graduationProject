package cn.yangcy.pzc.model.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.net.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UserRepository {

    private final static String TAG = "UserRepository";
    private SharedPreferences sharedPreferences;
    private UserDao userDao;
    private Gson gson = new Gson();

    public UserRepository(Context context) {
        DataBase dataBase = DataBase.getDataBase(context.getApplicationContext());
        userDao = dataBase.getUserDao();
        Log.i(TAG, "数据库连接");
        sharedPreferences = context.getSharedPreferences(Config.SP_NAME,Context.MODE_PRIVATE);

    }
    public void saveUserMessage(User user){
        Log.i(TAG, "保存用户信息 ");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_account",user.getAccount());
        editor.putInt("user_power",user.getPower());
        editor.apply();
    }

    public Integer getSpUserAccount(){
        return  sharedPreferences.getInt("user_account",-1);
    }
    public Integer getSpUserPower(){
        return  sharedPreferences.getInt("user_power",1);
    }

    public void register(User... users){
        Log.i(TAG, "register: 用户注册");
        new RegisterAsync(userDao).execute(users);
    }

    public void registerByNet(User user){
        Log.i(TAG, "register: 用户注册 By NET");
        String json;
        json = gson.toJson(user);
        System.out.println(json);
        final RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpResponse(Config.API_URL + Config.POST_USER_REGISTER, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });

    }

    public void updateUser(User user){
        Log.i(TAG, "用户修改");
        new UpdateAsync(userDao).execute(user);
    }
    public void updateUserByNet(final User user){
        Log.i(TAG, "用户修改(Net)");
        String json;
        json = gson.toJson(user);
        System.out.println(json);
        RequestBody requestBody = RequestBody.create(Config.JSON, json);
        HttpUtil.sendOkHttpPUT(Config.API_URL + Config.PUT_UPDATE_USER, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    saveUserMessage(user);
                }
                response.close();
            }
        });
    }

    public LiveData<User> queryUserByAccount(int account){
        Log.i(TAG, "通过用户Account查询User");
        return userDao.queryUserByAccount(account);
    }

    public LiveData<User> queryUserByAccountNet(int account){
        final MutableLiveData<User> queryUserByNet = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL+Config.GET_USER_BY_ACCOUNT + account, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (e instanceof ConnectException) {//判断连接异常，我这里是报Failed to connect to 10.7.5.144
                    return;
                }
                Log.e(TAG, "网络错误" + e.toString());

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String res = response.body().string();
                    Log.i(TAG, "onResponse: " + res);
                    User userResult = gson.fromJson(res, User.class);
                    queryUserByNet.postValue(userResult);
                }
                response.close();
            }
        });
        return queryUserByNet;
    }

    //TODO 多表查询
    public LiveData<List<User>> queryOrgMemberList(int organizationId){
        Log.i(TAG, "queryOrganizationMemberList: 多表查询");
        return userDao.queryOrgMemberList(organizationId);
    }

    public LiveData<List<User>> queryOrgMemberListNet(int organizationId){
        Log.i(TAG, "queryOrganizationMemberList: 多表查询Net");
        final MutableLiveData<List<User>> userList = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ORG_STATE2_USER_LIST_BY_ORG_ID + organizationId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String res = response.body().string();
                    Type type = new TypeToken<List<User>>(){}.getType();
                    List<User> resUserList = gson.fromJson(res,type);
                    userList.postValue(resUserList);
                }
                response.close();
            }
        });
        return userList;
    }

    public LiveData<List<User>> queryOrgEnrollMemberList(int organizationId){
        Log.i(TAG, "queryOrganizationEnrollMemberList: 多表查询");
        return userDao.queryOrgEnrollMemberList(organizationId);
    }

    public LiveData<List<User>> queryOrgEnrollMemberListNet(int organizationId){
        Log.i(TAG, "queryOrganizationMemberList: 多表查询Net");
        final MutableLiveData<List<User>> userList = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ORG_STATE1_USER_LIST_BY_ORG_ID + organizationId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String res = response.body().string();
                    Type type = new TypeToken<List<User>>(){}.getType();
                    List<User> resUserList = gson.fromJson(res,type);
                    userList.postValue(resUserList);
                }
                response.close();
            }
        });
        return userList;
    }

    public LiveData<List<User>> queryActMemberList(int activitiesId){
        Log.i(TAG, "queryActivitiesMemberList: 多表查询");
        return userDao.queryActMemberList(activitiesId);
    }

    public LiveData<List<User>> queryActMemberListNet(int activitiesId){
        Log.i(TAG, "queryOrganizationMemberList: 多表查询Net");
        final MutableLiveData<List<User>> userList = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ACT_STATE2_USER_LIST_BY_ACT_ID + activitiesId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String res = response.body().string();
                    Type type = new TypeToken<List<User>>(){}.getType();
                    List<User> resUserList = gson.fromJson(res,type);
                    userList.postValue(resUserList);
                }
                response.close();
            }
        });
        return userList;
    }

    public LiveData<List<User>> queryActEnrollMemberList(int activitiesId){
        Log.i(TAG, "queryActivitiesEnrollMemberList: 多表查询");
        return userDao.queryActEnrollMemberList(activitiesId);
    }

    public LiveData<List<User>> queryActEnrollMemberListNet(int activitiesId){
        Log.i(TAG, "queryOrganizationMemberList: 多表查询Net");
        final MutableLiveData<List<User>> userList = new MutableLiveData<>();
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.GET_ACT_STATE1_USER_LIST_BY_ACT_ID + activitiesId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    String res = response.body().string();
                    Type type = new TypeToken<List<User>>(){}.getType();
                    List<User> resUserList = gson.fromJson(res,type);
                    userList.postValue(resUserList);
                }
                response.close();
            }
        });
        return userList;
    }

    static class RegisterAsync extends AsyncTask<User,Void,Void> {
        private UserDao userDao;

        RegisterAsync(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            Log.i(TAG, "doInBackground: RegisterAsync");
            userDao.insertUser(users[0]);
            return null;
        }
    }

    static class UpdateAsync extends AsyncTask<User,Void,Void> {
        private UserDao userDao;

        UpdateAsync(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            Log.i(TAG, "doInBackground: UpdateAsync");
            userDao.updateUser(users[0]);
            return null;
        }
    }
}
