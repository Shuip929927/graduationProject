package cn.yangcy.pzc.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.ActivityLoginBinding;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.util.MD5Util;
import cn.yangcy.pzc.util.NetWorkUtil;
import cn.yangcy.pzc.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private LoginViewModel mLoginViewModel;
    private LiveData<User> checkUserExist;
    public static LoginActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        INSTANCE = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

//        mLoginViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication().)
//                .create(LoginViewModel.class);
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.setMLoginViewModel(mLoginViewModel);
        binding.setLifecycleOwner(this);


        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (mLoginViewModel.getAccount().getValue().isEmpty() || mLoginViewModel.getPassword().getValue().isEmpty()) {
                    Toast.makeText(getApplication(), R.string.err_empty, Toast.LENGTH_SHORT).show();
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    if(NetWorkUtil.isConnected(INSTANCE)){
                        Log.i(TAG, "访问网络数据 isNetConnection");
                    } else{
                        Log.i(TAG, "访问本地数据 isNetConnection");
                    }
                    if (NetWorkUtil.isConnected(INSTANCE)) {
                        Log.i(TAG, "访问网络数据");
                        checkUserExist = mLoginViewModel.doLoginByNet();
                    } else {
                        Log.i(TAG, "访问本地数据");
                        checkUserExist = mLoginViewModel.doLogin();
                    }
                    checkUserExist.observe(INSTANCE, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if (user == null) {
                                Log.d(TAG, "doLogin: queryUser Null");
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplication(), R.string.err_user_no_exist, Toast.LENGTH_SHORT).show();
                            } else {
                                binding.progressBar.setVisibility(View.GONE);
                                if (MD5Util.digest(mLoginViewModel.getPassword().getValue()).equals(user.getPassword())) {
                                    Log.d(TAG, "doLogin: queryUser Exist");
                                    Log.d(TAG, "doLogin: " + new Gson().toJson(user));
                                    mLoginViewModel.saveUser(user);
                                    Toast.makeText(INSTANCE, R.string.info_login_success, Toast.LENGTH_SHORT).show();
                                    Intent login = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(login);
                                    if (checkUserExist.hasObservers()) {
                                        Log.i(TAG, "移除checkUserExist 的观察者 ");
                                        checkUserExist.removeObserver(this);
                                    }
                                    finish();
                                } else {
                                    Toast.makeText(getApplication(), R.string.err_password, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
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
        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
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
