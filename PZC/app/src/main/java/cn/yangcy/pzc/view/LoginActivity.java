package cn.yangcy.pzc.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.ActivityLoginBinding;
import cn.yangcy.pzc.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding activityLoginBinding;
    LoginViewModel mLoginViewModel;
    //测试数据库存储
//    LiveData<List<User>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        getSupportActionBar().hide();

        mLoginViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(LoginViewModel.class);

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

        activityLoginBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(toRegister);
                finish();
            }
        });
    }
}
