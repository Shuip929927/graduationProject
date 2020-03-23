package cn.yangcy.pzc.model.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.model.DataBase;
import cn.yangcy.pzc.net.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class UserRepository {

    private final static String TAG = "UserRepository";
    private SharedPreferences sharedPreferences;
    private UserDao userDao;
    private MutableLiveData<User> queryUserByNet = new MutableLiveData<>();

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

    public void updateUser(User user){
        Log.i(TAG, "用户修改");
        new UpdateAsync(userDao).execute(user);
    }

    public LiveData<User> queryUserByAccount(int account){
        Log.i(TAG, "通过用户Account查询User");
        return userDao.queryUserByAccount(account);
    }

    public LiveData<User> queryUserByAccountNet(int account){
        HttpUtil.sendOkHttpRequest(Config.API_URL + Config.USER_QUERY_ACCOUNT + account, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                queryUserByNet.setValue(null);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String res = response.body().string();
                    Log.i(TAG, "onResponse: " + res);
                    Gson gson = new Gson();
                    User userResult = gson.fromJson(res, User.class);
                    queryUserByNet.postValue(userResult);
                }
            }
        });
        return queryUserByNet;
    }
    public LiveData<List<User>> queryUserLiveData(List<Integer> list) {
        Log.i(TAG, "通过用户列表查询一些用户");
        int[] accounts = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, "将用户List转为String[]: "+ list.get(i));
            accounts[i] = list.get(i);
        }
        return userDao.queryMemberLiveList(accounts);
    }

    //TODO 多表查询
//    public LiveData<List<User>> queryMemberList(int organizationId){
//        Log.i(TAG, "queryMemberList: 多表查询");
//        return userDao.queryMemberList(organizationId);
//    }
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
