package cn.yangcy.pzc.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import cn.yangcy.pzc.Config;
import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.ActivityLoginBinding;
import cn.yangcy.pzc.net.HttpUtil;
import cn.yangcy.pzc.util.JsonResult;
import cn.yangcy.pzc.util.MD5Util;
import cn.yangcy.pzc.viewmodel.LoginViewModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    ActivityLoginBinding activityLoginBinding;
    LoginViewModel mLoginViewModel;
    public static LoginActivity INSTANCE;

    //测试数据库存储
//    LiveData<List<User>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        INSTANCE = this;
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

//        mLoginViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication().)
//                .create(LoginViewModel.class);
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        activityLoginBinding.setMLoginViewModel(mLoginViewModel);
        activityLoginBinding.setLifecycleOwner(this);

        //测试数据库存储
//        list = mLoginViewModel.getAllUser();
//        list.observe(this, new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//                StringBuilder result = new StringBuilder();
//                for (int i = 0; i < users.size(); i++) {
//                    User u = users.get(i);
//                    result.append(u.toString()).append("\n");
//                }
//                activityLoginBinding.textView5.setText(result.toString());
//            }
//        });

        activityLoginBinding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLoginViewModel.doLogin()){
                    Intent login = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(login);
                    finish();
                }
            }
        });


                //todo:netWork
//                String account = mLoginViewModel.getAccount().getValue();
//                String password = mLoginViewModel.getPassword().getValue();
//                if (account.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, R.string.err_empty, Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    String json = "";
//                    json = getLoginJson(account,MD5Util.digest(password));
//                    final RequestBody requestBody = RequestBody.create(Config.JSON, json);
//                    HttpUtil.sendOkHttpResponse(Config.API_URL + Config.USER_LOGIN,
//                            requestBody, new Callback() {
//                                @Override
//                                public void onFailure(Call call, IOException e) {
//                                    Looper.prepare();
//                                    Toast.makeText(LoginActivity.this, "服务器错误，请稍后再试", Toast.LENGTH_SHORT).show();
//                                    Looper.loop();
//                                }
//                                @Override
//                                public void onResponse(Call call, Response response) throws IOException {
//                                    Gson gson = new Gson();
//                                    if (response.code() == 200) {
//                                        String res = response.body().string();
//                                        Log.i(TAG, "onResponse: "+ res);
//                                        JsonResult jsonResult = gson.fromJson(res, JsonResult.class);
//                                        if (Config.FIAL_STATE.equals(jsonResult.getStatus())) {
//                                            Looper.prepare();
//                                            Toast.makeText(LoginActivity.this, jsonResult.getResult().toString(), Toast.LENGTH_LONG).show();
//                                            Looper.loop();
//                                            return;
//                                        } else if (Config.SUCCESS_STATE.equals(jsonResult.getStatus())) {
//                                            Looper.prepare();
//                                            Toast.makeText(LoginActivity.this, jsonResult.getResult().toString(), Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                            startActivity(intent);
//                                            Looper.loop();
//                                            finish();
//                                        }
//                                    }
//                                }
//                            });
//                }
//            }
//        });
        activityLoginBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toRegister);
                finish();
            }
        });
    }

//    private String getLoginJson(String account, String password) {
//        JSONObject jsonParam = new JSONObject();
//        try {
//            jsonParam.put("account", account);
//            jsonParam.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonParam.toString();
//    }
}
