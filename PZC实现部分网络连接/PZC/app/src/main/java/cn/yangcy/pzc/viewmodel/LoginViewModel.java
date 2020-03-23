package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserRepository;
import cn.yangcy.pzc.util.NetWorkUtil;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";
    private UserRepository userRepository;
    private MutableLiveData<String> account;
    private MutableLiveData<String> password;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "获得UserRepository的实例");
        userRepository = new UserRepository(application);
    }

    public MutableLiveData<String> getAccount() {
        if (account == null) {
            account = new MutableLiveData<String>();
            account.setValue("");
            Log.d(TAG, "获取当前输入的账号 " + account.getValue());
        }
        return account;
    }

    public void setAccount(MutableLiveData<String> account) {
        this.account = account;
        Log.d(TAG, "动态绑定账号: " + account.getValue());
    }

    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<String>();
            password.setValue("");
            Log.d(TAG, "获取当前输入的密码: " + password.getValue());
        }
        return password;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
        Log.d(TAG, "动态绑定密码: " + password.getValue());
    }

    public LiveData<User> doLogin() {
        Log.d(TAG, "登录操作");
        Log.i(TAG, "doLogin: ROOM");
        return userRepository.queryUserByAccount(Integer.parseInt(Objects.requireNonNull(account.getValue())));

    }

    public LiveData<User> doLoginByNet(){
        Log.d(TAG, "登录操作");
        Log.i(TAG, "doLogin: NET");
        return userRepository.queryUserByAccountNet(Integer.parseInt(Objects.requireNonNull(account.getValue())));
    }

    public void saveUser(User user){
        userRepository.saveUserMessage(user);
    }

    private String getLoginJson(String account,String password) {
        Log.i(TAG, "将账号密码由String转换为JSON ");
        JSONObject jsonParam=new JSONObject();
        try {
            jsonParam.put("account",account);
            jsonParam.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam.toString();
    }

}



